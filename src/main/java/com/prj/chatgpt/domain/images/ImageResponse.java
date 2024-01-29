package com.prj.chatgpt.domain.images;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: Image response
 */
@Data
public class ImageResponse implements Serializable {
    /** Data of items */
    private List<Item> data;
    /** Created time */
    private long created;
}
