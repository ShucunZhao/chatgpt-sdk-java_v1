package com.prj.chatgpt.session.defaults;

import cn.hutool.http.ContentType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prj.chatgpt.IOpenAiApi;
import com.prj.chatgpt.domain.chat.ChatCompletionRequest;
import com.prj.chatgpt.domain.chat.ChatCompletionResponse;
import com.prj.chatgpt.domain.qa.QACompletionRequest;
import com.prj.chatgpt.domain.qa.QACompletionResponse;
import com.prj.chatgpt.session.Configuration;
import com.prj.chatgpt.session.OpenAiSession;
import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

import java.io.Serializable;

public class DefaultOpenAiSession implements OpenAiSession{

    //We send the configuration information to replace only openai here:
//    private IOpenAiApi openAiApi;
    private final Configuration configuration;

    /** OpenAI interface */
    private final IOpenAiApi openAiApi;
    /** Factory event */
    private final EventSource.Factory factory;

    public DefaultOpenAiSession(Configuration configuration) {
        this.configuration = configuration;
        this.openAiApi = configuration.getOpenAiApi();
        this.factory = configuration.createRequestFactory();
    }

    @Override
    public QACompletionResponse completions(QACompletionRequest qaCompletionRequest) {
        return this.openAiApi.completions(qaCompletionRequest).blockingGet();
    }

    @Override
    public EventSource completions(QACompletionRequest qaCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException {
        // Core parameter verification: Don't change parameter for user by default and only throws exception that parameter need to be true.
        if (!qaCompletionRequest.isStream()){
            throw new RuntimeException("Illegal parameter 'stream' is false!");
        }

        //Construct the information of request
        Request request = new Request.Builder()
                .url(configuration.getApiHost().concat(IOpenAiApi.v1_completions))
                .post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()), new ObjectMapper().writeValueAsString(qaCompletionRequest)))
                .build();

        // return event result
        return factory.newEventSource(request, eventSourceListener);
    }

    @Override
    public QACompletionResponse completions(String question) {
        QACompletionRequest request = QACompletionRequest
                .builder()
                .prompt(question)
                .build();
        Single<QACompletionResponse> completions = this.openAiApi.completions(request);
        return completions.blockingGet();
    }

    @Override
    public ChatCompletionResponse completions(ChatCompletionRequest chatCompletionRequest) {
        return this.openAiApi.completions(chatCompletionRequest).blockingGet();
    }

    @Override
    public EventSource chatCompletions(ChatCompletionRequest chatCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException {
        // Core parameter verification: Don't change parameter for user by default and only throws exception that parameter need to be true.
        if (!chatCompletionRequest.isStream()) {
            throw new RuntimeException("Illegal parameter 'stream' is false!");
        }

        //Construct the information of request
        Request request = new Request.Builder()
                // url: https://api.openai.com/v1/chat/completions - Obtain configuration information by fixed way through the POST interface configured by IOpenAiApi.
                .url(configuration.getApiHost().concat(IOpenAiApi.v1_chat_completions))
                // Encapsulates request parameter information. If Fastjson is used, it can also replace the ObjectMapper conversion object.
                .post(RequestBody.create(MediaType.parse(ContentType.JSON.getValue()), new ObjectMapper().writeValueAsString(chatCompletionRequest)))
                .build();

        // Return result information: EventSource object can cancel the response
        return factory.newEventSource(request, eventSourceListener);
    }
}
