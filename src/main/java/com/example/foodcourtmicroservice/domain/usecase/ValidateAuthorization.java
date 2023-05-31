package com.example.foodcourtmicroservice.domain.usecase;


import com.example.foodcourtmicroservice.domain.exception.UserNoAutorizedException;
import com.example.foodcourtmicroservice.configuration.Constants;

public class ValidateAuthorization {
    public ValidateAuthorization() {
    }
    public static void validateRole(String rolUser, String rolAutorized){
        if(!rolUser.equalsIgnoreCase(rolAutorized)){
            throw new UserNoAutorizedException(Constants.UNAUTHORIZED_USER);
        }
    }

}
