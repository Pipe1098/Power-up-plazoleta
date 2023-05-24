package com.example.foodcourtmicroservice.adapters.driving.http.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantResponseDto {
    private Long id;
    private String name;
    private String nit;
    private String adress;
    private String phone;
    private String urlLogo;
    private Long idOwner;
}
