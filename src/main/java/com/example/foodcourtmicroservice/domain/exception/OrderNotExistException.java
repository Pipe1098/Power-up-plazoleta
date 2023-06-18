package com.example.foodcourtmicroservice.domain.exception;


public class OrderNotExistException extends RuntimeException{
    public OrderNotExistException(String message) {
        super(message);
    }
}
