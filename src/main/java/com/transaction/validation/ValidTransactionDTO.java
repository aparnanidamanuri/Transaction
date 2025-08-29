package com.transaction.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TransactionDTOValidator.class)
public @interface ValidTransactionDTO {
    String message() default "TransactionDTO failed custom validations";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
