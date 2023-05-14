package com.example.foodcourtmicroservice.domain.spi;

import com.example.foodcourtmicroservice.domain.model.Restaurant;

public interface IRestaurantPersistencePort {
    void saveRestaurant(Restaurant restaurant);

    Restaurant getRestaurantByIdOwnwer(Long id);
}
