package com.example.foodcourtmicroservice.domain.usecase;

public class Token {
    private static String token;
    private static String pin;

    public static String getToken() {
        return Token.token;
    }
    public static void setToken(String token) {
        Token.token = token;
    }
    public static String getPin() {
        return Token.pin;
    }
    public static void setPin(String pin) {
        Token.pin = pin;
    }
}
