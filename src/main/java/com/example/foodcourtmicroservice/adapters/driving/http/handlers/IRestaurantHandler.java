package com.example.foodcourtmicroservice.adapters.driving.http.handlers;

import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;

public interface IRestaurantHandler {
    void saveRestaurant(RestaurantRequestDto restaurantRequestDto);
}
