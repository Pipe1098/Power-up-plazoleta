package com.example.foodcourtmicroservice.domain.spi;


import com.example.foodcourtmicroservice.domain.model.Dish;
import com.example.foodcourtmicroservice.domain.model.OrderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDishPersistencePort {
    void saveDish(Dish dish);
    void updateDish(Dish dish);
    Dish getDish(Long id);
    List<Dish> findAllByRestaurantId(Long idRestaurant, Integer page, Integer size);
    List<Dish> findAllByRestaurantIdAndCategory(Long category, Long idRestaurante, Integer page, Integer size);

    Page<OrderModel> findByState(String state, Pageable pageable);

    Page<OrderModel> findAll(Pageable pageable);
}
