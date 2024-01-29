package com.prj.chatgpt.domain.whisper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * @description Request information; speech to text
 */
@Data
public class WhisperEnum implements Serializable {
    @Getter
    @AllArgsConstructor
    public enum Model {
        WHISPER_1("whisper-1"),
        ;
        private String code;
    }

    @Getter
    @AllArgsConstructor
    public enum ResponseFormat {
        JSON("json"),
        TEXT("text"),
        SRT("srt"),
        VERBOSE_JSON("verbose_json"),
        VTT("vtt"),
        ;
        private String code;
    }
}
