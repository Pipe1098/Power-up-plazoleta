package com.example.foodcourtmicroservice.domain.exception;

public class DishNotExistException extends RuntimeException {
    public DishNotExistException(String message) {
        super(message);
    }
}
