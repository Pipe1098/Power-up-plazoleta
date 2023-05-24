package com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.exceptions;

public class NitAlredyRegistredException extends RuntimeException{
    public NitAlredyRegistredException(String message) {
        super(message);
    }
}
