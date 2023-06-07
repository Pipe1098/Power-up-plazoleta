package com.example.foodcourtmicroservice.domain.exception;

public class ClientHasAnOrderException extends RuntimeException {
    public ClientHasAnOrderException(String message) {
        super(message);
    }
}
