package com.prj.chatgpt;

import com.prj.chatgpt.domain.chat.ChatCompletionRequest;
import com.prj.chatgpt.domain.chat.ChatCompletionResponse;
import com.prj.chatgpt.domain.qa.QACompletionRequest;
import com.prj.chatgpt.domain.qa.QACompletionResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @description Define the interface based on the ChatGPT official website API model.
 * URL：https://platform.openai.com/playground
 */
public interface IOpenAiApi {

    String v1_completions = "v1/completions";

    /**
     * Text Q&A
     * @param qaCompletionRequest request information
     * @return                    response result
     */
    @POST(v1_completions)
    Single<QACompletionResponse> completions(@Body QACompletionRequest qaCompletionRequest);

    String v1_chat_completions = "v1/chat/completions";
    /**
     * Default GPT-3.5 Q&A model
     * @param chatCompletionRequest request information
     * @return                      response result
     */
    @POST(v1_chat_completions)
    Single<ChatCompletionResponse> completions(@Body ChatCompletionRequest chatCompletionRequest);
}