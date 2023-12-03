package com.example.BankingApplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank
    String pin;
}
