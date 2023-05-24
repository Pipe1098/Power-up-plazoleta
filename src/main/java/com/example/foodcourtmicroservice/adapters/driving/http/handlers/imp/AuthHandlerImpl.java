package com.example.foodcourtmicroservice.adapters.driving.http.handlers.imp;


import com.example.foodcourtmicroservice.adapters.driving.http.handlers.IAuthHandler;
import com.example.foodcourtmicroservice.domain.api.IAuthServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthHandlerImpl implements IAuthHandler {
    private final IAuthServicePort authServicePort;
    @Override
    public void saveToken(String token) {
        authServicePort.saveToken(token);
    }
}
