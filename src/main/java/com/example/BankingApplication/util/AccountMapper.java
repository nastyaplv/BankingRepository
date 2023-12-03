package com.example.BankingApplication.util;

import com.example.BankingApplication.dto.AccountRequestDto;
import com.example.BankingApplication.dto.AccountResponseDto;
import com.example.BankingApplication.dto.DepositDto;
import com.example.BankingApplication.dto.TransactionResponseDto;
import com.example.BankingApplication.entity.Account;
import com.example.BankingApplication.entity.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Component
public class AccountMapper {
    private final ModelMapper modelMapper;

    public AccountMapper() {
        this.modelMapper = new ModelMapper();
    }

    public AccountResponseDto convertToAccountResponseDto(Account account){
        return modelMapper.map(account, AccountResponseDto.class);
    }

    public Account convertToAccountEntity(AccountRequestDto accountRequestDto){
        return modelMapper.map(accountRequestDto, Account.class);
    }

    public TransactionResponseDto convertToTransactionResponseDto(Transaction transaction) {
        return modelMapper.map(transaction, TransactionResponseDto.class);
    }


}
