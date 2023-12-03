package com.example.BankingApplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AccountRequestDto {

    @NotBlank
    String beneficiaryName;

    @Pattern(regexp = "\\d+", message = "Pin должен содержать только цифры")
    @Size(min = 4, max = 4, message = "Account pin must be 4 characters long")
    @NotBlank
    String pin;
}
