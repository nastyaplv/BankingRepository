package com.example.BankingApplication.service;

import com.example.BankingApplication.entity.Account;
import com.example.BankingApplication.entity.Transaction;
import com.example.BankingApplication.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService{
    TransactionRepository transactionRepository;

    @Transactional
    @Override
    public List<Transaction> getAccountTransactions(Account account) {
        return transactionRepository.findAllByAccount(account);
    }
}
