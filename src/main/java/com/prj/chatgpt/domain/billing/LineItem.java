package com.prj.chatgpt.domain.billing;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description Consume list data
 */
@Data
public class LineItem {
    /** Model */
    private String name;
    /** Amount */
    private BigDecimal cost;
}
