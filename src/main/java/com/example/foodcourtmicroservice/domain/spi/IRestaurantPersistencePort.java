package com.example.foodcourtmicroservice.domain.spi;

import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.example.foodcourtmicroservice.domain.model.Restaurant;

import java.util.List;
import java.util.Optional;

public interface IRestaurantPersistencePort {

    void saveRestaurant(Restaurant restaurant);

    Restaurant getRestaurant(Long idRestaurant);

    Restaurant getRestaurantByIdOwnwer(Long id);

    List<Restaurant> getAllRestaurants();

    List<Restaurant> getRestaurantsWithPagination(Integer page, Integer size);
}
