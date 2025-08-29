package com.transaction.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "alerts")
@Data
public class AlertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Column(name = "transaction_id")
    private String transactionId;
    private String alertType;
    private String message;
    private Timestamp sentAt;

//    @OneToOne
//    @JoinColumn(name = "flagged_transaction_id")
//    private FlaggedTransactionsEntity flaggedTransaction;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "used_id", nullable = false)
//    private UserEntity user;
}
