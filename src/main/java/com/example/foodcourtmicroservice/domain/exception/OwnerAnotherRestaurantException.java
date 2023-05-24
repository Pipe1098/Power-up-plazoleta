package com.example.foodcourtmicroservice.domain.exception;

public class OwnerAnotherRestaurantException extends RuntimeException{
    public OwnerAnotherRestaurantException(String message) {
        super(message);
    }
}
