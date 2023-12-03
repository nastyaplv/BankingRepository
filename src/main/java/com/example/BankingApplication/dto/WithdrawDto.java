package com.example.BankingApplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class WithdrawDto {

    @Pattern(regexp = "\\d+", message = "Pin должна содержать только цифры")
    @NotBlank
    String pin;

    @Positive
    BigDecimal amount;

}
