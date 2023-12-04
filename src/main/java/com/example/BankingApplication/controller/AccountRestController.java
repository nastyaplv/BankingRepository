package com.example.BankingApplication.controller;

import com.example.BankingApplication.dto.*;
import com.example.BankingApplication.entity.Account;
import com.example.BankingApplication.entity.Transaction;
import com.example.BankingApplication.exception.EntityNotFoundException;
import com.example.BankingApplication.exception.IncorrectPinException;
import com.example.BankingApplication.exception.InsuficientFundsException;
import com.example.BankingApplication.service.AccountService;
import com.example.BankingApplication.service.TransactionService;
import com.example.BankingApplication.util.AccountMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("account")
@AllArgsConstructor
public class AccountRestController {
    private AccountService accountService;
    private TransactionService transactionService;
    private AccountMapper accountMapper;

   @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid AccountRequestDto dto){
       Account account = accountMapper.convertToAccountEntity(dto);
       accountService.createAccount(account.getBeneficiaryName(), account.getPin());
       return ResponseEntity.status(HttpStatus.CREATED).body("Account was created");
   }

   @GetMapping("/accounts")
    public ResponseEntity<List<AccountResponseDto>> getAllAccounts(){
       List<Account> accountList= accountService.getAllAccounts();

       List<AccountResponseDto> accountResponseDtoList = accountList.stream()
               .map(a->accountMapper.convertToAccountResponseDto(a)).collect(Collectors.toList());

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

       List<TransactionResponseDto> transactionResponseDtoList = list.stream()
               .map(a -> accountMapper.convertToTransactionResponseDto(a)).collect(Collectors.toList());

       return ResponseEntity.status(HttpStatus.OK).body(transactionResponseDtoList);
   }

    @ExceptionHandler({IncorrectPinException.class, InsuficientFundsException.class, EntityNotFoundException.class})
    public ResponseEntity<String> handleExceptions(Exception exception) {

        if (exception instanceof IncorrectPinException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect pin entered");
        } else if (exception instanceof InsuficientFundsException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds");
        } else if (exception instanceof EntityNotFoundException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entity not found");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }

}
