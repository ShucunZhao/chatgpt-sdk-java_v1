package com.prj.chatgpt.domain.other;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Usage implements Serializable {
    /** Prompt tokens definition */
    @JsonProperty("prompt_tokens")
    private long promptTokens;
    /** Completion tokens definition */
    @JsonProperty("completion_tokens")
    private long completionTokens;
    /** Total tokens definition */
    @JsonProperty("total_tokens")
    private long totalTokens;

    public long getPromptTokens() {
        return promptTokens;
    }

    public void setPromptTokens(long promptTokens) {
        this.promptTokens = promptTokens;
    }

    public long getCompletionTokens() {
        return completionTokens;
    }

    public void setCompletionTokens(long completionTokens) {
        this.completionTokens = completionTokens;
    }

    public long getTotalTokens() {
        return totalTokens;
    }

    public void setTotalTokens(long totalTokens) {
        this.totalTokens = totalTokens;
    }
}
