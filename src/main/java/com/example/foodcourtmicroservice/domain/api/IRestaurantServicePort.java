package com.example.foodcourtmicroservice.domain.api;

import com.example.foodcourtmicroservice.domain.model.Restaurant;

public interface IRestaurantServicePort {
    void saveRestaurant(Restaurant restaurant);

}
