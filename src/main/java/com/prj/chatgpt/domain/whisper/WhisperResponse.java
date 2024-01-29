package com.prj.chatgpt.domain.whisper;

import lombok.Data;

import java.io.Serializable;

@Data
public class WhisperResponse implements Serializable {
    private String text;
}
