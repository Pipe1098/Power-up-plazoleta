package com.example.foodcourtmicroservice.adapters.driving.http.feigns;


import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.AuthRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.JwtResponseDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.example.foodcourtmicroservice.configuration.FeignClientConfig;
import com.example.foodcourtmicroservice.domain.model.UserModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "user-microservice", url = "${external.usermicroservice.base-url}",configuration = FeignClientConfig.class)
public interface RestaurantFeignClient {

    @GetMapping(value = "/api/v1/user/validate-owner/{dni}")
    Boolean validateOwner(@PathVariable("dniNumber") String dni);

    @PostMapping(value = "/auth/login")
    ResponseEntity<JwtResponseDto> login(@RequestBody AuthRequestDto authRequestDto);

    @GetMapping(value = "/auth/dni/{token}")
    String idUser(@PathVariable("token") String token);

    @GetMapping(value = "/auth/role/{token}")
    String roleUser(@PathVariable("token") String token);

    @GetMapping(value = "/auth/idRestaurant/{token}")
    String idRestaurantUser(@PathVariable("token") String token);

    @GetMapping(value = "/auth/mail/{token}")
    String getmailToken (@PathVariable("token") String token);

    @GetMapping(value = "/api/v1/user/name/{dni}")
    String getUserNameById(String idUser);
}
