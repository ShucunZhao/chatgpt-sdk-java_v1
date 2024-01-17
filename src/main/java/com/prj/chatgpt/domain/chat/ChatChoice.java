package com.prj.chatgpt.domain.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ChatChoice implements Serializable {
    private long index;
    /** stream = true, the attribute returned in the request parameter is delta */
    @JsonProperty("delta")
    private Message delta;
    /** stream = false, the attribute returned in the request parameter is delta */
    @JsonProperty("message")
    private Message message;
    @JsonProperty("finish_reason")
    private String finishReason;
}
