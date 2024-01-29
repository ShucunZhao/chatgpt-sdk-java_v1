package com.prj.chatgpt.domain.images;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * @description: Image enum configuration
 */
public class ImageEnum implements Serializable {
    @Getter
    @AllArgsConstructor
    public enum Size {
        size_256("256x256"),
        size_512("512x512"),
        size_1024("1024x1024"),
        ;
        private String code;
    }

    @Getter
    @AllArgsConstructor
    public enum ResponseFormat {
        URL("url"),
        B64_JSON("b64_json"),
        ;
        private String code;
    }
}
