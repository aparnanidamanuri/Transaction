package com.transaction.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FraudRulesDTO {

    private String ruleName;
    private String ruleDescription;
    private BigDecimal thresholdAmount;
    private Boolean enabled;
}
