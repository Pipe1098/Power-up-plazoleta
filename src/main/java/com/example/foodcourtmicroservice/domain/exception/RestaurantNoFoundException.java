package com.example.foodcourtmicroservice.domain.exception;

public class RestaurantNoFoundException extends RuntimeException{
    public RestaurantNoFoundException(String message) {
        super(message);
    }
}
