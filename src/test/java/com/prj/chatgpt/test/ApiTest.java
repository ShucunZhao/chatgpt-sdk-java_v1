package com.prj.chatgpt.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prj.chatgpt.common.Constants;
import com.prj.chatgpt.domain.billing.BillingUsage;
import com.prj.chatgpt.domain.billing.Subscription;
import com.prj.chatgpt.domain.chat.ChatCompletionRequest;
import com.prj.chatgpt.domain.chat.ChatCompletionResponse;
import com.prj.chatgpt.domain.chat.Message;
import com.prj.chatgpt.domain.edits.EditRequest;
import com.prj.chatgpt.domain.edits.EditResponse;
import com.prj.chatgpt.domain.embedd.EmbeddingResponse;
import com.prj.chatgpt.domain.files.DeleteFileResponse;
import com.prj.chatgpt.domain.files.UploadFileResponse;
import com.prj.chatgpt.domain.images.ImageEnum;
import com.prj.chatgpt.domain.images.ImageRequest;
import com.prj.chatgpt.domain.images.ImageResponse;
import com.prj.chatgpt.domain.other.OpenAiResponse;
import com.prj.chatgpt.domain.qa.QACompletionRequest;
import com.prj.chatgpt.domain.qa.QACompletionResponse;
import com.prj.chatgpt.session.Configuration;
import com.prj.chatgpt.session.OpenAiSession;
import com.prj.chatgpt.session.OpenAiSessionFactory;
import com.prj.chatgpt.session.defaults.DefaultOpenAiSessionFactory;
import lombok.extern.slf4j.Slf4j;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.CountDownLatch;

/**
 * description: single unit testing
 */
@Slf4j
public class ApiTest {

    private OpenAiSession openAiSession;

    @Before
    public void test_OpenAiSessionFactory() {
        //1.Configure file
        Configuration configuration = new Configuration();
//        configuration.setApiHost("https://service-d6wuqy4n-1320869466.cd.apigw.tencentcs.com/");//From xfg
        configuration.setApiHost("https://api.openai.com/");//Official
//        configuration.setApiHost("https://api.openai-proxy.com/");//Proxy of official
//        configuration.setApiKey("sk-xxxxx");//From xfg key
        configuration.setApiKey("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");//Official key
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
        log.info("Tested result：{}", new ObjectMapper().writeValueAsString(response01.getChoices()));
    }

    /**
     * Stream response question and answer mode:
     */
    @Test
    public void test_qa_completions_stream() throws JsonProcessingException, InterruptedException {
        // 1. Create parameters
        QACompletionRequest request = QACompletionRequest
                .builder()
                .prompt("写个java冒泡排序")
                .stream(true)
                .build();

        for (int i = 0; i < 1; i++) {
            // 2. Send request
            EventSource eventSource = openAiSession.completions(request, new EventSourceListener() {
                @Override
                public void onEvent(EventSource eventSource, String id, String type, String data) {
                    log.info("Tested result：{}", data);
                }
            });
        }

        // Waiting
        new CountDownLatch(1).await();
    }

    /**
     * Simple chat mode:
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
            log.info("Tested result：{}", e.getMessage());
        });
    }

    /**
     * Stream response chat mode:
     */
    @Test
    public void test_chat_completions_stream() throws JsonProcessingException, InterruptedException {
        // 1. Create parameter
        ChatCompletionRequest chatCompletion = ChatCompletionRequest
                .builder()
                .stream(true)
                .messages(Collections.singletonList(Message.builder().role(Constants.Role.USER).content("写一个java冒泡排序").build()))
                .model(ChatCompletionRequest.Model.GPT_3_5_TURBO.getCode())
                .build();
        // 2. Send request
        EventSource eventSource = openAiSession.chatCompletions(chatCompletion, new EventSourceListener() {
            @Override
            public void onEvent(EventSource eventSource, String id, String type, String data) {
                log.info("测试结果：{}", data);
            }
        });
        // Waiting
        new CountDownLatch(1).await();
    }

    /**
     * Continuous contextual conversation function
     */
    @Test
    public void test_chat_completions_context() {
        // 1-1. Create parameters
        ChatCompletionRequest chatCompletion = ChatCompletionRequest
                .builder()
                .messages(new ArrayList<>())
                .model(ChatCompletionRequest.Model.GPT_4.getCode())
                .user("testUser01")
                .build();
        // Write in the request information
        chatCompletion.getMessages().add(Message.builder().role(Constants.Role.USER).content("写一个java冒泡排序").build());

        // 1-2. Ask for request
        ChatCompletionResponse chatCompletionResponse01 = openAiSession.completions(chatCompletion);
        log.info("Tested result：{}", chatCompletionResponse01.getChoices());

        // Continue writing in the request information
        chatCompletion.getMessages().add(Message.builder().role(Constants.Role.USER).content(chatCompletionResponse01.getChoices().get(0).getMessage().getContent()).build());
        chatCompletion.getMessages().add(Message.builder().role(Constants.Role.USER).content("换一种写法").build());

        ChatCompletionResponse chatCompletionResponse02 = openAiSession.completions(chatCompletion);
        log.info("Tested result：{}", chatCompletionResponse02.getChoices());
    }

    /**
     * Text edit: This model seems un-work right now, just use the gpt-4 chat instead.
     */
    @Test
    public void test_edit() {
        // Text request
        EditRequest textRequest = EditRequest.builder()
                .input("请问现在是基点")
                .instruction("帮我修改错字")
                .model(EditRequest.Model.TEXT_DAVINCI_EDIT_001.getCode()).build();
        EditResponse textResponse = openAiSession.edit(textRequest);
        log.info("Tested result：{}", textResponse);

        // Code request
        EditRequest codeRequest = EditRequest.builder()
                // j <= 10 Shouble be i <= 10
                .input("for (int i = 1; j <= 10; i++) {\n" +
                        "    System.out.println(i);\n" +
                        "}")
                .instruction("这段代码执行时报错，请帮我修改").model(EditRequest.Model.CODE_DAVINCI_EDIT_001.getCode()).build();
        EditResponse codeResponse = openAiSession.edit(codeRequest);
        log.info("Tested result：{}", codeResponse);
    }

    /**
     * Image generation
     */
    @Test
    public void test_genImages() {
        // Method1: Simple invoke
        ImageResponse imageResponse01 = openAiSession.genImages("画一个小丑");
        log.info("Tested result：{}", imageResponse01);

        // Method2: set parameters invoke
        ImageResponse imageResponse02 = openAiSession.genImages(ImageRequest.builder()
                .prompt("画一个小丑")
                .size(ImageEnum.Size.size_256.getCode())
                .responseFormat(ImageEnum.ResponseFormat.B64_JSON.getCode()).build());
        log.info("Tested result：{}", imageResponse02);
    }

    /**
     * Edit image，three different methods with different parameters
     */
    @Test
    public void test_editImages() throws IOException {
        ImageResponse imageResponse = openAiSession.editImages(new File("E:/Codes/JavaBeginner/Projects/chatgpt-sdk-java_v2/docs/images/2.png"), "把鼻子涂成红色”");
        log.info("Tested result：{}", imageResponse);
    }

    @Test
    public void test_embeddings() {
        EmbeddingResponse embeddingResponse = openAiSession.embeddings("哈喽", "嗨", "hi!");
        log.info("Tested result：{}", embeddingResponse);
    }

    @Test
    public void test_files() {
        OpenAiResponse<File> openAiResponse = openAiSession.files();
        log.info("Tested result：{}", openAiResponse);
    }

    @Test
    public void test_uploadFile() {
        UploadFileResponse uploadFileResponse = openAiSession.uploadFile(new File("E:/Codes/JavaBeginner/Projects/chatgpt-sdk-java_v2/docs/files/introduction.jsonl"));
        log.info("Tested result：{}", uploadFileResponse);
    }

    /**
     * The fileid needs to be obtained after uploading the file in the previous step.
     * Last step response information: {
     *   "object": "file",
     *   "id": "file-vIPelH3ppYbwasHfM9iBBH6a",
     *   "purpose": "fine-tune",
     *   "filename": "introduction.jsonl",
     *   "bytes": 248,
     *   "created_at": 1706522584,
     *   "status": "processed",
     *   "status_details": null
     * }
     * ---------------------------------
     * This step response information: {
     *   "object": "file",
     *   "id": "file-vIPelH3ppYbwasHfM9iBBH6a",
     *   "deleted": true
     * }
     */
    @Test
    public void test_deleteFile() {
        DeleteFileResponse deleteFileResponse = openAiSession.deleteFile("file-vIPelH3ppYbwasHfM9iBBH6a");//file id 上传后才能获得
        log.info("Tested resultn：{}", deleteFileResponse);
    }

    /**
     * Response: {
     *   "error": {
     *     "message": "Your request to GET /v1/dashboard/billing/subscription must be made with a session key (that is, it can only be made from the browser). You made it with the following key type: secret.",
     *     "type": "server_error",
     *     "param": null,
     *     "code": null
     *   }
     * }
     */
    @Test
    public void test_subscription() {
        Subscription subscription = openAiSession.subscription();
        log.info("Tested result：{}", subscription);
    }

    /**
     * Response: {
     *   "error": {
     *     "message": "Your request to GET /v1/dashboard/billing/usage must be made with a session key (that is, it can only be made from the browser). You made it with the following key type: secret.",
     *     "type": "server_error",
     *     "param": null,
     *     "code": null
     *   }
     * }
     */
    @Test
    public void test_billingUsage() {
        BillingUsage billingUsage = openAiSession.billingUsage(LocalDate.of(2023, 3, 20), LocalDate.now());
        log.info("Tested result：{}", billingUsage.getTotalUsage());
    }
}
