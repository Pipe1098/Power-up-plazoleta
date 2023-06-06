package com.example.foodcourtmicroservice.domain.spi;


import com.example.foodcourtmicroservice.domain.model.Dish;
import java.util.List;

public interface IDishPersistencePort {
    void saveDish(Dish dish);
    void updateDish(Dish dish);
    Dish getDish(Long id);
    List<Dish> findAllByRestaurantId(Long idRestaurant, Integer page, Integer size);
    List<Dish> findAllByRestaurantIdAndCategory(String category, Long idRestaurante, Integer page, Integer size);
}
