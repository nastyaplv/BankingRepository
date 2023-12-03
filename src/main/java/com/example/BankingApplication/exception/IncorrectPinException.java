package com.example.BankingApplication.exception;

public class IncorrectPinException extends RuntimeException{
    public IncorrectPinException(String message){
        super(message);
    }
}
