package com.transaction.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionException extends RuntimeException{

    private String errorMsg;
    private HttpStatus errorStatusCode;

}
