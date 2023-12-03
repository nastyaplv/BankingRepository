package com.example.BankingApplication.service;

import com.example.BankingApplication.entity.Account;
import com.example.BankingApplication.entity.Transaction;

import java.util.List;

public interface TransactionService {
List<Transaction> getAccountTransactions(Account account);
}
