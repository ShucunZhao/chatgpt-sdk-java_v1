package com.prj.chatgpt.session;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.prj.chatgpt.domain.chat.ChatCompletionRequest;
import com.prj.chatgpt.domain.chat.ChatCompletionResponse;
import com.prj.chatgpt.domain.qa.QACompletionRequest;
import com.prj.chatgpt.domain.qa.QACompletionResponse;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;

public interface OpenAiSession {
    /**
     * Text Q&A: simple request, generally for testing.
     * @param question request information
     * @return return result
     */
    QACompletionResponse completions(String question);

    /**
     * Default text Q&A
     * @param qaCompletionRequest request information
     * @return                    response result
     */
    QACompletionResponse completions(QACompletionRequest qaCompletionRequest);

    /**
     * Text Q&A & streaming response request
     * @param qaCompletionRequest request information
     * @param eventSourceListener implements monitoring: receives data through the onEvent method of monitoring
     */
    EventSource completions(QACompletionRequest qaCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException;

    /**
     * Default GPT-3.5/4.0 chat model
     * @param chatCompletionRequest request information
     * @return return result
     */
    ChatCompletionResponse completions(ChatCompletionRequest chatCompletionRequest);

    /**
     * GPT-3.5/4.0 chat model & streaming response request
     * @param chatCompletionRequest request information
     * @param eventSourceListener implements monitoring: receives data through the onEvent method of monitoring
     * @return return result
     */
    EventSource chatCompletions(ChatCompletionRequest chatCompletionRequest, EventSourceListener eventSourceListener) throws JsonProcessingException;
}
