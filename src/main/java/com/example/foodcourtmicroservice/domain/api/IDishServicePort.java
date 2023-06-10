package com.example.foodcourtmicroservice.domain.api;


import com.example.foodcourtmicroservice.domain.model.Dish;

import java.util.List;

public interface IDishServicePort {
    void saveDish(Dish dish);
    void updateDish(Long id, String price, String description);
    void updateEnableDisableDish(Long dishId, Long enableDisable);
    List<Dish> findAllByRestaurantId(Long idRestaurante, Integer page, Integer size);
    List<Dish> findAllByRestaurantIdAndCategory(Long idRestaurante, Long category, Integer page, Integer size);
}
