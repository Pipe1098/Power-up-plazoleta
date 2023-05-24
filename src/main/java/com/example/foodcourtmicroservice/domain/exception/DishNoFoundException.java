package com.example.foodcourtmicroservice.domain.exception;

public class DishNoFoundException extends RuntimeException {
    public DishNoFoundException(String message) {
        super(message);
    }
}
