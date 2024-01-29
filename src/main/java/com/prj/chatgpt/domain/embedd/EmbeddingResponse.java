package com.prj.chatgpt.domain.embedd;

import com.prj.chatgpt.domain.other.Usage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: response object
 */
@Data
public class EmbeddingResponse implements Serializable {
    private String object;
    private List<Item> data;
    private String model;
    private Usage usage;
}
