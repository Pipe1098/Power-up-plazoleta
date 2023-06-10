package com.example.foodcourtmicroservice.adapters.driving.http.handlers;


import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.UpdateDishRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.DishResponseDto;
import java.util.List;

public interface IDishHandler {
    void saveDish(DishRequestDto dishRequestDto);
    void updateDish(UpdateDishRequestDto updateDishRequestDto);
    void updateEnableDisableDish(Long dishId, Long enableDisable);
    List<DishResponseDto> findAllByRestaurantId(Long idRestaurante, Integer page, Integer size);
    List<DishResponseDto> findAllByRestaurantIdAndCategory(Long idRestaurante, Long category, Integer page, Integer size);
}
