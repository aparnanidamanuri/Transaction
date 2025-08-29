package com.transaction.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class FlaggedTransactionsDTO {

    private String transactionId;
    private String reason;
    private Timestamp flaggedAt;
}
