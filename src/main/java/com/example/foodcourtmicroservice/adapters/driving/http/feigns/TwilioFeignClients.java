package com.example.foodcourtmicroservice.adapters.driving.http.feigns;

import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.SmsMessageRequestDto;
import com.example.foodcourtmicroservice.configuration.FeignClientConfig;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "message-microservice", url = "${external.messagemicroservice.base-url}",configuration = FeignClientConfig.class)
public interface TwilioFeignClients {
    @PostMapping("/")
    ResponseEntity<Void> sendSmsMessage(@Valid @RequestBody SmsMessageRequestDto smsMessageRequestDto);
}
