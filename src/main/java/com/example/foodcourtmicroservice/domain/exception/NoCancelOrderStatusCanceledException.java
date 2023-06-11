package com.example.foodcourtmicroservice.domain.exception;

public class NoCancelOrderStatusCanceledException extends RuntimeException {
    public NoCancelOrderStatusCanceledException(String message) {
        super(message);
    }
}

