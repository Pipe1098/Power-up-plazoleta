package com.example.foodcourtmicroservice.domain.exception;

public class DishIdRestaurantIsNotEqualsOrderException extends  RuntimeException{
    public DishIdRestaurantIsNotEqualsOrderException(String message) {
        super(message);
    }
}
