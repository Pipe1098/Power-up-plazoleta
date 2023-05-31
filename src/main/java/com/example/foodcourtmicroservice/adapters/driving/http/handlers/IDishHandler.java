package com.example.foodcourtmicroservice.adapters.driving.http.handlers;


import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.UpdateDishRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.DishRequestDto;

public interface IDishHandler {
    void saveDish(DishRequestDto dishRequestDto);
    void updateDish(UpdateDishRequestDto updateDishRequestDto);
    void updateEnableDisableDish(Long dishId, Long enableDisable);
}
