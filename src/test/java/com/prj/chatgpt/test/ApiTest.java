package com.prj.chatgpt.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prj.chatgpt.common.Constants;
import com.prj.chatgpt.domain.chat.ChatCompletionRequest;
import com.prj.chatgpt.domain.chat.ChatCompletionResponse;
import com.prj.chatgpt.domain.chat.Message;
import com.prj.chatgpt.domain.qa.QACompletionResponse;
import com.prj.chatgpt.session.Configuration;
import com.prj.chatgpt.session.OpenAiSession;
import com.prj.chatgpt.session.OpenAiSessionFactory;
import com.prj.chatgpt.session.defaults.DefaultOpenAiSessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

@Slf4j
public class ApiTest {
    private OpenAiSession openAiSession;

    @Before
    public void test_OpenAiSessionFactory() {
        //1.Configure file
        Configuration configuration = new Configuration();
//        configuration.setApiHost("https://api.openai-proxy.com/");
        configuration.setApiHost("https://api.openai.com/");
        configuration.setApiKey("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");
        // 测试时候，需要先获得授权token：http://api.xfg.im:8080/authorize?username=xfg&password=123 - 此地址暂时有效，后续根据课程首页说明获取token；https://t.zsxq.com/0d3o5FKvc
        //configuration.setAuthToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4ZmciLCJleHAiOjE2ODMyODE2NzEsImlhdCI6MTY4MzI3ODA3MSwianRpIjoiMWUzZTkwYjYtY2UyNy00NzNlLTk5ZTYtYWQzMWU1MGVkNWE4IiwidXNlcm5hbWUiOiJ4ZmcifQ.YgQRJ2U5-9uydtd6Wbkg2YatsoX-y8mS_OJ3FdNRaX0");
        // 2. Session factory
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);
        // 3. Open session
        this.openAiSession = factory.openSession();
    }

    /**
     * Simple question and answer mode:
     */
    @Test
    public void test_qa_completions() throws JsonProcessingException {
        QACompletionResponse response01 = openAiSession.completions("写个java冒泡排序");
        log.info("测试结果：{}", new ObjectMapper().writeValueAsString(response01.getChoices()));
    }

    /**
     * 此对话模型 3.5 接近于官网体验
     *
     * 文档：https://platform.openai.com/docs/guides/text-generation/chat-completions-api
     * 你可以替换能访问的 apihost【https://api.openai.com】 和 $OPENAI_API_KEY 进行 http 测试
     * curl https://api.openai.com/v1/chat/completions \
     *   -H "Content-Type: application/json" \
     *   -H "Authorization: Bearer $OPENAI_API_KEY" \
     *   -d '{
     *     "model": "gpt-3.5-turbo",
     *     "messages": [
     *       {
     *         "role": "system",
     *         "content": "You are a helpful assistant."
     *       },
     *       {
     *         "role": "user",
     *         "content": "Who won the world series in 2020?"
     *       },
     *       {
     *         "role": "assistant",
     *         "content": "The Los Angeles Dodgers won the World Series in 2020."
     *       },
     *       {
     *         "role": "user",
     *         "content": "Where was it played?"
     *       }
     *     ]
     *   }'
     */
    @Test
    public void test_chat_completions() {
        // 1. Create parameters
        ChatCompletionRequest chatCompletion = ChatCompletionRequest
                .builder()
                .messages(Collections.singletonList(Message.builder().role(Constants.Role.USER).content("写一个java冒泡排序").build()))
                .model(ChatCompletionRequest.Model.GPT_3_5_TURBO.getCode())
                .build();
        // 2. Ask for request
        ChatCompletionResponse chatCompletionResponse = openAiSession.completions(chatCompletion);
        // 3. Parse results
        chatCompletionResponse.getChoices().forEach(e -> {
            log.info("测试结果：{}", e.getMessage());
        });
    }

}
