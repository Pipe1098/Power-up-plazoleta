package com.example.foodcourtmicroservice.domain.exception;

public class NoCancelOrdersPreparationOrReadyException extends RuntimeException {
    public NoCancelOrdersPreparationOrReadyException(String message) {
        super(message);
    }

}
