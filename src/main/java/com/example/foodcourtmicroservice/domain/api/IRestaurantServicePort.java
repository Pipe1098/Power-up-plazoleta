package com.example.foodcourtmicroservice.domain.api;

import com.example.foodcourtmicroservice.domain.model.Restaurant;
import com.example.foodcourtmicroservice.domain.model.UserModel;

public interface IRestaurantServicePort {
    void saveRestaurant(Restaurant restaurant);

    boolean existsUserById(Long idOwner);

    UserModel getUserById(Long idOwner);
}
