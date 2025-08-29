package com.transaction.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FraudRuleException extends RuntimeException {
    private String errorMsg;
    private HttpStatus errorStatusCode;
}