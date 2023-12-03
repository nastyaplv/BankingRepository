package com.example.BankingApplication.dto;

import com.example.BankingApplication.entity.Account;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
    @NotBlank
    String pin;

    @Positive
    BigDecimal amount;
}
