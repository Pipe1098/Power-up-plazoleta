package com.example.foodcourtmicroservice.adapters.driving.http.exceptions;

public class UserNotPermissionException extends RuntimeException {
    public UserNotPermissionException(String message){
        super(message);
    }
}
