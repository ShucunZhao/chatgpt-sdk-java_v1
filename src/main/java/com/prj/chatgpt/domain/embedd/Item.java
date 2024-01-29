package com.prj.chatgpt.domain.embedd;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @description: item information
 */
@Data
public class Item implements Serializable {
    private String object;
    private List<BigDecimal> embedding;
    private Integer index;
}
