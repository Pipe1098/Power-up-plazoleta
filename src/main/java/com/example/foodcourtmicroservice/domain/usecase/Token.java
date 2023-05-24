package com.example.foodcourtmicroservice.domain.usecase;

public class Token {
    private static String token;

    public static String getToken() {
        return Token.token;
    }
    public static void setToken(String token) {
        Token.token = token;
    }
}
