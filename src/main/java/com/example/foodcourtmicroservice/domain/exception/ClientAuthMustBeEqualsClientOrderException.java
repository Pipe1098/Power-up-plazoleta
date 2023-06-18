package com.example.foodcourtmicroservice.domain.exception;

public class ClientAuthMustBeEqualsClientOrderException extends RuntimeException{
    public ClientAuthMustBeEqualsClientOrderException(String message) {
        super(message);
    }
}
