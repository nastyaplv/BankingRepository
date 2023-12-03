package com.example.BankingApplication.dto;

import com.example.BankingApplication.entity.Account;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TransferDto {

    @NotNull
    UUID toAccount;

    @Pattern(regexp = "\\d+", message = "Pin должен содержать только цифры")
    @Size(min = 4, max = 4, message = "Account pin must be 4 characters long")
    @NotBlank
    String pin;

    @Positive
    BigDecimal amount;
}
