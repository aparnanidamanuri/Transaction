package com.transaction.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Table(name = "flagged_transactions")
@Data
public class FlaggedTransactionsEntity {

    /*
    id SERIAL PRIMARY KEY,
    transaction_id VARCHAR(50) UNIQUE NOT NULL,
    reason TEXT,
    flagged_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "transaction_id", nullable = false, unique = true, insertable = false, updatable = false)
    private String transactionId;
    private String reason;
    private Timestamp flaggedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id",
        referencedColumnName = "transaction_id",
            nullable = false
    )
    private TransactionEntity transaction;
//
//    @OneToOne(mappedBy = "flaggedTransaction", cascade = CascadeType.ALL)
//    private AlertEntity alertEntity;
}
