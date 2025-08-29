package com.transaction.validation;

import com.transaction.dto.TransactionDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TransactionDTOValidator implements ConstraintValidator<ValidTransactionDTO, TransactionDTO> {

    private final Set<String> merchantsAllowed = new HashSet<>(Arrays.asList(
            "Amazon", "Apple Store", "Best Buy", "Costco", "Home Depot", "Kroger", "Lowes", "Sam's Club", "Target", "Walmart"));

    private final Set<String> currenciesAllowed = new HashSet<>(Arrays.asList("AUD", "EUR", "INR", "USD"));

    @Override
    public boolean isValid(TransactionDTO transactionDTO, ConstraintValidatorContext context){
        boolean isValid = true;
        context.disableDefaultConstraintViolation();

        //Transaction ID: not null, pattern match
        if(transactionDTO.getTransactionId() == null || !transactionDTO.getTransactionId().matches("TXN\\d{4,}")){
            context.buildConstraintViolationWithTemplate("Transaction ID must be in the TXN#### format")
                    .addPropertyNode("transactionId").addConstraintViolation();

            isValid = false;
        }

        if(transactionDTO.getUserId() == null || !transactionDTO.getUserId().matches("USR\\d{4,}")){
            context.buildConstraintViolationWithTemplate("User ID must be in the USR#### format")
                    .addPropertyNode("userId").addConstraintViolation();
            isValid = false;
        }

        if(transactionDTO.getMerchant() == null || !merchantsAllowed.contains(transactionDTO.getMerchant())){
            context.buildConstraintViolationWithTemplate("Merchants must be one of: "+merchantsAllowed)
                    .addPropertyNode("merchant").addConstraintViolation();

            isValid = false;
        }

        if(transactionDTO.getCurrency() == null || !currenciesAllowed.contains(transactionDTO.getCurrency())){
            context.buildConstraintViolationWithTemplate("Currencies must be one of: "+currenciesAllowed)
                    .addPropertyNode("currency").addConstraintViolation();

            isValid = false;
        }

        if(transactionDTO.getAmount() == null || transactionDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            context.buildConstraintViolationWithTemplate("Amount must be greater than 0")
                    .addPropertyNode("amount").addConstraintViolation();

            isValid = false;
        }

        if(transactionDTO.getTimestamp() == null || transactionDTO.getTimestamp().after(Timestamp.from(Instant.now()))){
            context.buildConstraintViolationWithTemplate("Timestamp must not be in the future")
                    .addPropertyNode("timestamp").addConstraintViolation();

            isValid = false;
        }

        if (transactionDTO.getLocation() != null && transactionDTO.getLocation().trim().length() < 2) {
            context.buildConstraintViolationWithTemplate("Location must have at least 2 characters")
                    .addPropertyNode("location").addConstraintViolation();
            isValid = false;
        }

        return isValid;

    }
}
