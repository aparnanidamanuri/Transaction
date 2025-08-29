package com.transaction.dto;

import com.transaction.validation.ValidTransactionDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@ValidTransactionDTO
public class TransactionDTO {

    //private Integer id;
    private String transactionId;
    private String userId;
    private String merchant;
    private BigDecimal amount;
    private String currency;
    private String location;
    private Timestamp timestamp;
}
