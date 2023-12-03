package com.example.BankingApplication.exception;

import lombok.Getter;
import lombok.Setter;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String message) {
        super(message);
    }
}
