package com.transaction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class TransactionExceptionHandler {

    @ExceptionHandler(TransactionException.class)
    public ResponseEntity<Map<String, String>> handleCustomGlobalException(TransactionException transactionException, WebRequest webRequest){
        Map<String, String> response = new HashMap<>();

        response.put("error",transactionException.getErrorMsg());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<Object> handleUserException(UserException ex) {

        Object errors = ex.getErrorMsg();

        if(errors instanceof String){
            Map<String, String> response = new HashMap<>();
            response.put("error",(String) ex.getErrorMsg());
            return ResponseEntity.status(ex.getErrorStatusCode()).body(response);
        }



        return ResponseEntity.status(ex.getErrorStatusCode()).body(errors);
    }

    @ExceptionHandler(AlertException.class)
    public ResponseEntity<Map<String, String>> handleAlertException(AlertException ex, WebRequest request) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getErrorMsg());
        return ResponseEntity.status(ex.getErrorStatusCode()).body(response);
    }

    @ExceptionHandler(FraudRuleException.class)
    public ResponseEntity<Map<String, String>> handleFraudRuleException(FraudRuleException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getErrorMsg());
        return ResponseEntity.status(ex.getErrorStatusCode()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String, String>> handleMethodValidation(HandlerMethodValidationException ex){
        Map<String, String> errors = new HashMap<>();

        ex.getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            errors.put(field, error.getDefaultMessage());
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_GATEWAY);
    }
}
