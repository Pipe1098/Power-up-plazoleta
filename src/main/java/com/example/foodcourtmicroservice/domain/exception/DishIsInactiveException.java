package com.example.foodcourtmicroservice.domain.exception;

public class DishIsInactiveException extends RuntimeException {
    public DishIsInactiveException(String m) {
        super(m);
    }
}
