package com.prj.chatgpt.domain.other;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OpenAiResponse<T> implements Serializable {
    @Data
    public class Error {
        private String message;
        private String type;
        private String param;
        private String code;
    }
    private String object;
    private List<T> data;
    private Error error;
    // Add has_more field
    private boolean has_more;
}
