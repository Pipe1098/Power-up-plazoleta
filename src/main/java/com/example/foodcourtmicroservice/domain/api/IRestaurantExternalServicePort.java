package com.example.foodcourtmicroservice.domain.api;

import com.example.foodcourtmicroservice.domain.model.UserModel;

public interface IRestaurantExternalServicePort {

    String getRoleFromToken(String token);
    String getIdFromToken(String token);
    boolean existsUserById(Long idOwner);
    boolean validateOwner(String idOwner);
    UserModel getUserById(String idOwner);
}
