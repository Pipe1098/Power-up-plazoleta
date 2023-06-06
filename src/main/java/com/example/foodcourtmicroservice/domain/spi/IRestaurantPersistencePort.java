package com.example.foodcourtmicroservice.domain.spi;

import com.example.foodcourtmicroservice.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantPersistencePort {

    void saveRestaurant(Restaurant restaurant);

    Restaurant getRestaurant(Long idRestaurantAux);

    Restaurant getRestaurantByIdOwnwer(Long id);

    List<Restaurant> getAllRestaurants();

    List<Restaurant> getRestaurantsWithPagination(Integer page, Integer size);
}
