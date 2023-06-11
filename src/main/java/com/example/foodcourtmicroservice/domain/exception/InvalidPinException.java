package com.example.foodcourtmicroservice.domain.exception;

public class InvalidPinException extends RuntimeException {
    public InvalidPinException(String message) {
        super(message);
    }

}
