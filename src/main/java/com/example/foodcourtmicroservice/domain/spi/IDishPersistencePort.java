package com.example.foodcourtmicroservice.domain.spi;


import com.example.foodcourtmicroservice.domain.model.Dish;

public interface IDishPersistencePort {
    void saveDish(Dish dish);
    void updateDish(Dish dish);
    Dish getDish(Long id);
}
