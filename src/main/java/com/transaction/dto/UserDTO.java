package com.transaction.dto;

import com.transaction.validation.ValidTransactionDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDTO {

    //@NotNull(message = "User ID can't be null")
    private String userId;
    //@NotBlank(message = "User name can't be blank")
    private String name;
    //@Email(message = "Invalid email format")
    private String email;
    private Timestamp registeredAt;
}
