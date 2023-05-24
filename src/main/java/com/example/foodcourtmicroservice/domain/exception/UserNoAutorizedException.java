package com.example.foodcourtmicroservice.domain.exception;

public class UserNoAutorizedException extends RuntimeException{
    public UserNoAutorizedException(String message) {
        super(message);
    }
}
