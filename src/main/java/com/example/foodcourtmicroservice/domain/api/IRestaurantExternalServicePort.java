package com.example.foodcourtmicroservice.domain.api;

import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.example.foodcourtmicroservice.domain.model.UserModel;

public interface IRestaurantExternalServicePort {
    //void saveRestaurant(RestaurantRequestDto restaurantRequestDto);
    String getRoleFromToken(String token);
    String getIdOwnerFromToken(String token);
    boolean existsUserById(Long idOwner);
    boolean validateOwner(String idOwner);
    UserModel getUserById(String idOwner);
}
