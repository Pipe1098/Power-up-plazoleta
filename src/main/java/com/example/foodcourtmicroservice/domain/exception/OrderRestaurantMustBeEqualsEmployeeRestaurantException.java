package com.example.foodcourtmicroservice.domain.exception;

public class OrderRestaurantMustBeEqualsEmployeeRestaurantException extends RuntimeException {
    public OrderRestaurantMustBeEqualsEmployeeRestaurantException(String message) {
        super(message);
    }
}
