package com.example.foodcourtmicroservice.domain.api;


import com.example.foodcourtmicroservice.domain.model.Dish;

public interface IDishServicePort {
    void saveDish(Dish dish);
    void updateDish(Long id, String price, String description);
}
