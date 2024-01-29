package com.prj.chatgpt.test;

import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prj.chatgpt.IOpenAiApi;
import com.prj.chatgpt.common.Constants;
import com.prj.chatgpt.domain.chat.ChatCompletionRequest;
import com.prj.chatgpt.domain.chat.ChatCompletionResponse;
import com.prj.chatgpt.domain.chat.Message;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.junit.Test;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Collections;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HttpClientTest {

    @Test
    public void test_client(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // 设置连接超时时间
                .readTimeout(30, TimeUnit.SECONDS)    // 设置读取超时时间
                .writeTimeout(30, TimeUnit.SECONDS)   // 设置写入超时时间
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();

                    /**
                     * Get the token parameter from the request and add it to the request path
                     */
                    HttpUrl url = original.url().newBuilder()
                            .addQueryParameter("token", "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")
                            .build();

                    Request request = original.newBuilder()
                            .url(url)
                            .header(Header.AUTHORIZATION.getValue(), "Bearer " + "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")
                            .header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .build();

        IOpenAiApi openAiApi = new Retrofit.Builder()
                .baseUrl("https://api.openai.com/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(IOpenAiApi.class);

        Message message = Message.builder().role(Constants.Role.USER).content("写一个java冒泡排序").build();
        ChatCompletionRequest chatCompletion = ChatCompletionRequest
                .builder()
                .messages(Collections.singletonList(message))
                .model(ChatCompletionRequest.Model.GPT_3_5_TURBO.getCode())
                .build();

        Single<ChatCompletionResponse> chatCompletionResponseSingle = openAiApi.completions(chatCompletion);
        ChatCompletionResponse chatCompletionResponse = chatCompletionResponseSingle.blockingGet();
        chatCompletionResponse.getChoices().forEach(e -> {
            System.out.println(e.getMessage());
        });
    }

    @Test
    public void test_client_stream() throws JsonProcessingException, InterruptedException {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .connectTimeout(30, TimeUnit.SECONDS) // 设置连接超时时间
                .readTimeout(30, TimeUnit.SECONDS)    // 设置读取超时时间
                .writeTimeout(30, TimeUnit.SECONDS)   // 设置写入超时时间
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    HttpUrl url = original.url().newBuilder() // Get the token parameter from the request and add it to the request path
                            .addQueryParameter("token", "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")
                            .build();

                    Request request = original.newBuilder()
                            .url(url)
                            .header(Header.AUTHORIZATION.getValue(), "Bearer " + "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")
                            .header(Header.CONTENT_TYPE.getValue(), ContentType.JSON.getValue())
                            .method(original.method(), original.body())
                            .build();
                    return chain.proceed(request);
                })
                .build();

        Message message = Message.builder().role(Constants.Role.USER).content("写一个java冒泡排序").build();
        ChatCompletionRequest chatCompletion = ChatCompletionRequest
                .builder()
                .messages(Collections.singletonList(message))
                .model(ChatCompletionRequest.Model.GPT_3_5_TURBO.getCode())
                .stream(true)
                .build();

        //Add some event here:
        EventSource.Factory factory = EventSources.createFactory(okHttpClient);
        String  requestBody = new ObjectMapper().writeValueAsString(chatCompletion);
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()), requestBody))
                .build();
        EventSource eventSource = factory.newEventSource(request, new EventSourceListener() {
            @Override
            public void onEvent(EventSource eventSource, String id, String type, String data) {
                log.info("Testing result: {}", data);
            }
        });
//        eventSource.cancel(); // If you need cancel this event use it
        //Waiting for response result
        new CountDownLatch(1).await();
    }
}
