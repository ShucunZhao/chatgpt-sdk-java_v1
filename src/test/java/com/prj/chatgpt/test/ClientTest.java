package com.prj.chatgpt.test;

import com.prj.chatgpt.common.Constants;
import com.prj.chatgpt.domain.chat.ChatCompletionRequest;
import com.prj.chatgpt.domain.chat.ChatCompletionResponse;
import com.prj.chatgpt.domain.chat.Message;
import com.prj.chatgpt.session.Configuration;
import com.prj.chatgpt.session.OpenAiSession;
import com.prj.chatgpt.session.OpenAiSessionFactory;
import com.prj.chatgpt.session.defaults.DefaultOpenAiSessionFactory;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @description Client enter testing
 */
public class ClientTest {
    public static void main(String[] args) throws InterruptedException {
        // 1. Configuration
        Configuration configuration = new Configuration();
        configuration.setApiHost("https://api.openai.com/");
        configuration.setApiKey("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");
        // 测试时候，需要先获得授权token：http://api.xfg.im:8080/authorize?username=xfg&password=123 - 此地址暂时有效，后续根据课程首页说明获取token；https://t.zsxq.com/0d3o5FKvc
//        configuration.setAuthToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4ZmciLCJleHAiOjE2ODQ2MzEwNjAsImlhdCI6MTY4NDYyNzQ2MCwianRpIjoiMGU2M2Q3NDctNDk1YS00NDU3LTk1ZTAtOWVjMzkwNTlkNmQzIiwidXNlcm5hbWUiOiJ4ZmcifQ.xX4kaw-Pz2Jm4LBSvADzijud4nlNLFQUOaN6UgxrK9E");

        // 2. Session factory
        OpenAiSessionFactory factory = new DefaultOpenAiSessionFactory(configuration);
        OpenAiSession openAiSession = factory.openSession();

        System.out.println("我是 OpenAI ChatGPT，请输入你的问题：");

        ChatCompletionRequest chatCompletion = ChatCompletionRequest
                .builder()
                .messages(new ArrayList<>())
                .model(ChatCompletionRequest.Model.GPT_3_5_TURBO.getCode())
                .user("testUser01")
                .build();

        // 3. Waiting for enter the question
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String text = scanner.nextLine();
            chatCompletion.getMessages().add(Message.builder().role(Constants.Role.USER).content(text).build());
            ChatCompletionResponse chatCompletionResponse = openAiSession.completions(chatCompletion);
            chatCompletion.getMessages().add(Message.builder().role(Constants.Role.USER).content(chatCompletionResponse.getChoices().get(0).getMessage().getContent()).build());
            // 输出结果
            System.out.println(chatCompletionResponse.getChoices().get(0).getMessage().getContent());
            System.out.println("请输入你的问题：");
        }

    }
}
