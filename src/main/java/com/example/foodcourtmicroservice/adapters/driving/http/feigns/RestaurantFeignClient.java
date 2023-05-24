package com.example.foodcourtmicroservice.adapters.driving.http.feigns;


import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.AuthRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.JwtResponseDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.example.foodcourtmicroservice.configuration.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "user-microservice", url = "${external.usermicroservice.base-url}",configuration = FeignClientConfig.class)
public interface RestaurantFeignClient {

    @GetMapping("/users/{dniNumber}")
    UserResponseDto getUserByDni(@PathVariable("dniNumber") String dniNumber);

    @PostMapping(value = "/auth/login")
    ResponseEntity<JwtResponseDto> login(@RequestBody AuthRequestDto authRequestDto);
}
