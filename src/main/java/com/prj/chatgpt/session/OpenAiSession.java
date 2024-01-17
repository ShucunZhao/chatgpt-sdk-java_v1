package com.prj.chatgpt.session;

import com.prj.chatgpt.domain.chat.ChatCompletionRequest;
import com.prj.chatgpt.domain.chat.ChatCompletionResponse;
import com.prj.chatgpt.domain.qa.QACompletionRequest;
import com.prj.chatgpt.domain.qa.QACompletionResponse;

public interface OpenAiSession {
    /**
     * Text Q&A
     * @param qaCompletionRequest request information
     * @return                    response result
     */
    QACompletionResponse completions(QACompletionRequest qaCompletionRequest);

    /**
     * Text Q&A: simple request
     * @param question request information
     * @return return result
     */
    QACompletionResponse completions(String question);

    /**
     * Default GPT-3.5 Q&A model
     * @param chatCompletionRequest request information
     * @return return result
     */
    ChatCompletionResponse completions(ChatCompletionRequest chatCompletionRequest);
}
