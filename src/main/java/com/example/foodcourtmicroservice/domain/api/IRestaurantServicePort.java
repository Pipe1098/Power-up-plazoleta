package com.example.foodcourtmicroservice.domain.api;

import com.example.foodcourtmicroservice.domain.model.Restaurant;
import com.example.foodcourtmicroservice.domain.model.UserModel;

import java.util.List;

public interface IRestaurantServicePort {
    void saveRestaurant(Restaurant restaurant);

    boolean existsUserById(Long idOwner);

    UserModel getUserById(Long idOwner);

    List<Restaurant> getAllRestaurants();
}
