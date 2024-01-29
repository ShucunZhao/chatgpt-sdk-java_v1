package com.prj.chatgpt.domain.images;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description: Item
 */
@Data
public class Item implements Serializable {
    private String url;
    @JsonProperty("b64_json")
    private String b64Json;
}
