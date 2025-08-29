package com.transaction.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Table(name = "transactions")
@Entity
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;
    @Column(name = "user_id")
    private String userId;
    @Column(name = "merchant")
    private String merchant;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "location")
    private String location;
    @Column(name = "currency")
    private String currency;
    @Column(name = "timestamp")
    private Timestamp timestamp;

    @ManyToMany
    @JoinTable(
            name = "flagged_rules",
            joinColumns = @JoinColumn(name = "transaction_id", referencedColumnName = "id", columnDefinition = "BIGINT UNSIGNED"),
            inverseJoinColumns = @JoinColumn(name = "rule_id", referencedColumnName = "id", columnDefinition = "BIGINT UNSIGNED")
    )
    private List<FraudRulesEntity> matchedRules;
}
