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
public class UserException extends RuntimeException{

    private Object errorMsg;
    private HttpStatus errorStatusCode;

}
