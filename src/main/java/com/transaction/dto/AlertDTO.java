package com.transaction.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class AlertDTO {

    private String transactionId;
    private String alertType;
    private String message;
    private Timestamp sentAt;
}
