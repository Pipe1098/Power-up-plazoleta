package com.example.foodcourtmicroservice.domain.api;

import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;

public interface IRestaurantExternalServicePort {
    void saveRestaurant(RestaurantRequestDto restaurantRequestDto);
}
