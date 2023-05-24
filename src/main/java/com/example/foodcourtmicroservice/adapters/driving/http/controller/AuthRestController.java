package com.example.foodcourtmicroservice.adapters.driving.http.controller;


import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.AuthRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.JwtResponseDto;
import com.example.foodcourtmicroservice.adapters.driving.http.feigns.RestaurantFeignClient;
import com.example.foodcourtmicroservice.adapters.driving.http.handlers.IAuthHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthRestController {
    private final RestaurantFeignClient usuarioFeignClient;
    private final IAuthHandler authHandler;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> login(@RequestBody @Valid AuthRequestDto authRequestDto) {
        ResponseEntity<JwtResponseDto> token = usuarioFeignClient.login(authRequestDto);
        authHandler.saveToken(token.getBody().getToken());
        return token;
    }

}
