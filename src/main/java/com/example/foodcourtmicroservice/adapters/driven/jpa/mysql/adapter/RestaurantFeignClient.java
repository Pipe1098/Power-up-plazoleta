package com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter;


import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "restaurant-service", url = "http://localhost:8090")
public interface RestaurantFeignClient {
    @GetMapping("/users/{dniNumber}")
    UserResponseDto getUserByDni(@PathVariable("dniNumber") String dniNumber);
}
