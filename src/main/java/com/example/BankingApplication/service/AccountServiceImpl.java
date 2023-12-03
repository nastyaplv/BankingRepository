package com.example.BankingApplication.service;

import com.example.BankingApplication.entity.Account;
import com.example.BankingApplication.entity.Transaction;
import com.example.BankingApplication.entity.TransactionType;
import com.example.BankingApplication.exception.EntityNotFoundException;
import com.example.BankingApplication.exception.IncorrectPinException;
import com.example.BankingApplication.exception.InsuficientFundsException;
import com.example.BankingApplication.repository.AccountRepository;
import com.example.BankingApplication.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    @Override
    public void createAccount(String name, String pin) {
        accountRepository.save(new Account(name, pin));
    }

    @Transactional
    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(UUID accountId) {
        return accountRepository.findById(accountId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Account with id#%s not found", accountId)));
    }

    @Transactional
    @Override
    public void withdraw(UUID accountId, String pin, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Account with id#%s not found", accountId)));

        if (account.getPin().equals(pin)) {
            if (account.getBalance().compareTo(amount) >= 0) {
                account.setBalance(account.getBalance().subtract(amount));
            } else {
                throw new InsuficientFundsException(String.format("There is no enough money to complete this operation for account with id#%s", accountId));
            }
            Transaction transaction = Transaction.builder()
                    .transactionType(TransactionType.WITHDRAWAL)
                    .dateTime(LocalDateTime.now())
                    .message("Withdrawal transaction")
                    .amount(amount)
                    .account(new HashSet<>())
                    .build();

            transaction.getAccount().add(account);
            account.getAccountTransactions().add(transaction);
            transactionRepository.save(transaction);

        } else {
            throw new IncorrectPinException("Incorrected pin entered");
        }
    }

    @Transactional
    @Override
    public void transfer(UUID fromAccountId, UUID toAccountId, String pin, BigDecimal amount) {
        Account fromAccount = accountRepository.findById(fromAccountId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Account sending money with id#%s not found", fromAccountId)));
        Account toAccount = accountRepository.findById(toAccountId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Account receiving money with id#%s not found", toAccountId)));

        if (fromAccount.getPin().equals(pin)) {
            if (fromAccount.getBalance().compareTo(amount) >= 0) {
                fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
                toAccount.setBalance(toAccount.getBalance().add(amount));

                Transaction transaction = Transaction.builder()
                        .transactionType(TransactionType.TRANSFER)
                        .dateTime(LocalDateTime.now())
                        .message("Transfer transaction")
                        .amount(amount)
                        .account(new HashSet<>())
                        .build();

                transaction.getAccount().add(fromAccount);
                transaction.getAccount().add(toAccount);
                fromAccount.getAccountTransactions().add(transaction);
                toAccount.getAccountTransactions().add(transaction);
                transactionRepository.save(transaction);

            } else {
                throw new InsuficientFundsException(String.format("There is no enough money to complete this operation for account with id#%s", fromAccountId));
            }
        } else {
            throw new IncorrectPinException("Incorrected pin entered");
        }

    }

    @Transactional
    @Override
    public void deposit(UUID accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).orElseThrow(() ->
                new EntityNotFoundException(String.format("Account with id#%s not found", accountId)));
        account.setBalance(account.getBalance().add(amount));

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.DEPOSIT)
                .dateTime(LocalDateTime.now())
                .message("Deposit transaction")
                .amount(amount)
                .account(new HashSet<>())
                .build();

        account.getAccountTransactions().add(transaction);
        transaction.getAccount().add(account);
        transactionRepository.save(transaction);

    }
}
