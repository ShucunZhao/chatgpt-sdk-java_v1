package com.prj.chatgpt.domain.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.prj.chatgpt.domain.other.Usage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ChatCompletionResponse implements Serializable {
    /** ID */
    private String id;
    /** Object */
    private String object;
    /** Model */
    private String model;
    /** Dialog */
    private List<ChatChoice> choices;
    /** Created */
    private long created;
    /** Usage */
    private Usage usage;
    /**
     * This fingerprint represents the backend configuration used when the model is run.
     * https://platform.openai.com/docs/api-reference/chat
     */
    @JsonProperty("system_fingerprint")
    private String systemFingerprint;
}
