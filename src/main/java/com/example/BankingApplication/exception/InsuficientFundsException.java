package com.example.BankingApplication.exception;

public class InsuficientFundsException extends RuntimeException {
    public InsuficientFundsException(String message) {
        super(message);
    }
}
