package com.example.foodcourtmicroservice.adapters.driving.http.dto.request;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String mail;
    private String password;
}
