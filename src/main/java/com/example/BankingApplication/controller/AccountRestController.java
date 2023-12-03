package com.example.BankingApplication.controller;

import com.example.BankingApplication.dto.*;
import com.example.BankingApplication.entity.Account;
import com.example.BankingApplication.entity.Transaction;
import com.example.BankingApplication.service.AccountService;
import com.example.BankingApplication.service.TransactionService;
import com.example.BankingApplication.util.AccountMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("account")
@AllArgsConstructor
public class AccountRestController {
    AccountService accountService;
    TransactionService transactionService;
    AccountMapper accountMapper;

   @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid AccountRequestDto dto){
       Account account = accountMapper.convertToAccountEntity(dto);
       accountService.createAccount(account.getBeneficiaryName(), account.getPin());
       return ResponseEntity.status(HttpStatus.CREATED).body("Account was created");
   }

   @GetMapping("/accounts")
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts(){
       List<Account> accountList= accountService.getAllAccounts();
       List<AccountResponseDto> accountResponseDtoList = new ArrayList<>();
       for (Account account: accountList) {
           AccountResponseDto accountResponseDto=accountMapper.convertToAccountResponseDto(account);
           accountResponseDtoList.add(accountResponseDto);
       }
       return ResponseEntity.status(HttpStatus.OK).body(accountResponseDtoList);
   }

   @PatchMapping("/deposit/{accountId}")
    public ResponseEntity<String> deposit(@PathVariable @NotNull UUID accountId,
                                        @RequestBody @Valid DepositDto dto) {
           accountService.deposit(accountId, dto.getAmount());
           return ResponseEntity.status(HttpStatus.OK).body(String.format("Money have been deposited into the account with id#%s", accountId));
   }

   @PatchMapping("/withdraw/{accountId}")
    public ResponseEntity<String> withdraw(@PathVariable @NotNull UUID accountId,
                                           @RequestBody @Valid WithdrawDto dto) {
       accountService.withdraw(accountId, dto.getPin(), dto.getAmount());
       return ResponseEntity.status(HttpStatus.OK).body(String.format("Money were withdrawn from the account with id#%s", accountId));
   }

   @PatchMapping("/transfer/{accountId}")
    public ResponseEntity<String> transfer(@PathVariable @NotNull UUID accountId,
                                           @RequestBody @Valid TransferDto dto){
       accountService.transfer(accountId, dto.getToAccount(), dto.getPin(), dto.getAmount());
       return ResponseEntity.status(HttpStatus.OK).
               body(String.format("Transfer was made from the account with id#%s to the account with id#%s", accountId, dto.getToAccount()));
   }

   @GetMapping("/transactions/{accountId}")
    public ResponseEntity<List<TransactionResponseDto>> getAllAccountTransactions(@PathVariable @NotNull UUID accountId) {
       Account account = accountService.getAccountById(accountId);
       List<Transaction> list = transactionService.getAccountTransactions(account);
       List<TransactionResponseDto> transactionResponseDtoList = new ArrayList<>();
       for (Transaction transaction: list) {
           TransactionResponseDto transactionResponseDto = accountMapper.convertToTransactionResponseDto(transaction);
           transactionResponseDtoList.add(transactionResponseDto);
       }
       return ResponseEntity.status(HttpStatus.OK).body(transactionResponseDtoList);
   }

}
