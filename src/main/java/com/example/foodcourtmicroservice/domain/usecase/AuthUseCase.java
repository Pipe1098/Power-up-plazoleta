package com.example.foodcourtmicroservice.domain.usecase;


import com.example.foodcourtmicroservice.domain.api.IAuthServicePort;

public class AuthUseCase implements IAuthServicePort {

    @Override
    public void saveToken(String token) {
        Token.setToken(token);
    }
}
