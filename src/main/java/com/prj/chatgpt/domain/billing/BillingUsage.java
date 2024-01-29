package com.prj.chatgpt.domain.billing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description bill consumption
 */
@Data
public class BillingUsage {
    @JsonProperty("object")
    private String object;
    /**Account amount consumption details */
    @JsonProperty("daily_costs")
    private List<DailyCost> dailyCosts;
    /** Total amount used/US dollar cents */
    @JsonProperty("total_usage")
    private BigDecimal totalUsage;
}
