package com.example.foodcourtmicroservice.adapters.driving.http.handlers.imp;

import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.UpdateDishRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.handlers.IDishHandler;
import com.example.foodcourtmicroservice.adapters.driving.http.mappers.IDishRequestDtoMapper;
import com.example.foodcourtmicroservice.domain.api.IDishServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DishHandlerImpl implements IDishHandler {
    private final IDishServicePort dishServicePort;
    private final IDishRequestDtoMapper dishRequestDtoMapper;

    @Override
    public void saveDish(DishRequestDto dishRequestDto) {
        dishServicePort.saveDish(dishRequestDtoMapper.toDish(dishRequestDto));
    }

    @Override
    public void updateDish(UpdateDishRequestDto updateDishRequestDto) {
        dishServicePort.updateDish(updateDishRequestDto.getId(), updateDishRequestDto.getPrice(), updateDishRequestDto.getDescription());
    }
}
