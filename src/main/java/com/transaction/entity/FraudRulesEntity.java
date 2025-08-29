package com.transaction.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "fraud_rules")
@Data
public class FraudRulesEntity {

    /*
    id SERIAL PRIMARY KEY,
    rule_name VARCHAR(100),
    rule_description TEXT,id
    threshold_amount DECIMAL(12, 2),
    enabled BOOLEAN DEFAULT true
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @Column(name = "rule_name")
    private String ruleName;
    private String ruleDescription;
    private BigDecimal thresholdAmount;
    private Boolean enabled;

    @ManyToMany(mappedBy = "matchedRules")
    private List<com.transaction.entity.TransactionEntity> matchedTransactions;

}
