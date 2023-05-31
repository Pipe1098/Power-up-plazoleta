package com.example.foodcourtmicroservice.domain.exception;

public class UserMustBeOwnerException extends  RuntimeException {
    public UserMustBeOwnerException(String message) {
        super(message);
    }
}
