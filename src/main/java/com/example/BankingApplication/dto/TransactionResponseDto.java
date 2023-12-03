package com.example.BankingApplication.dto;

import com.example.BankingApplication.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TransactionResponseDto {

    private long id;

    private String transactionType;

    private String message;

    private BigDecimal amount;

}
