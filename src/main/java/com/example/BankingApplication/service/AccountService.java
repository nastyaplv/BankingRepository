package com.example.BankingApplication.service;

import com.example.BankingApplication.entity.Account;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AccountService {
    void createAccount(String name, String pin);
    List<Account> getAllAccounts();
    Account getAccountById(UUID accountId);
    void withdraw(UUID accountId, String pin, BigDecimal amount);
    void transfer(UUID fromAccountId, UUID toAccountId, String pin, BigDecimal amount);
    void deposit(UUID accountId, BigDecimal amount);
}
