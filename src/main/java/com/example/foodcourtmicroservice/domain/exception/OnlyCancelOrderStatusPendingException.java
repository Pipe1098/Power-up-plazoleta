package com.example.foodcourtmicroservice.domain.exception;

public class OnlyCancelOrderStatusPendingException extends RuntimeException {
    public OnlyCancelOrderStatusPendingException(String message) {
        super(message);
    }

}
