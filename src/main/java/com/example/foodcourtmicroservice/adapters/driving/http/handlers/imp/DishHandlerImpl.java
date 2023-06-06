package com.example.foodcourtmicroservice.adapters.driving.http.handlers.imp;

import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.UpdateDishRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.example.foodcourtmicroservice.adapters.driving.http.handlers.IDishHandler;
import com.example.foodcourtmicroservice.adapters.driving.http.mappers.IDishRequestMapper;
import com.example.foodcourtmicroservice.adapters.driving.http.mappers.IDishResponseMapper;
import com.example.foodcourtmicroservice.configuration.Constants;
import com.example.foodcourtmicroservice.domain.api.IDishServicePort;
import com.example.foodcourtmicroservice.domain.exception.NoDataFoundException;
import com.example.foodcourtmicroservice.domain.model.Dish;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DishHandlerImpl implements IDishHandler {
    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestDtoMapper;
    private final IDishResponseMapper dishResponseMapper;

    @Override
    public void saveDish(DishRequestDto dishRequestDto) {
        dishServicePort.saveDish(dishRequestDtoMapper.toDish(dishRequestDto));
    }

    @Override
    public void updateDish(UpdateDishRequestDto updateDishRequestDto) {
        dishServicePort.updateDish(updateDishRequestDto.getId(), updateDishRequestDto.getPrice(), updateDishRequestDto.getDescription());
    }

    @Override
    public void updateEnableDisableDish(Long dishId, Long enableDisable) {
        dishServicePort.updateEnableDisableDish(dishId, enableDisable);
    }

    @Override
    public List<DishResponseDto> findAllByRestaurantId(Long idRestaurante, Integer page, Integer size) {
        List<Dish> dishList = dishServicePort.findAllByRestaurantId(idRestaurante,page,size);
        if(dishList.isEmpty()){
            throw new NoDataFoundException(Constants.DISH_NOT_FOUND);
        }
        return dishResponseMapper.toResponseList(dishList);
    }

    @Override
    public List<DishResponseDto> findAllByRestaurantIdAndCategory(Long idRestaurante, String category, Integer page, Integer size) {
        List<Dish> dishList = dishServicePort.findAllByRestaurantIdAndCategory(idRestaurante,category,page,size);
        if(dishList.isEmpty()){
            throw new NoDataFoundException(Constants.DISH_NOT_FOUND);
        }
        return dishResponseMapper.toResponseList(dishList);
    }
}
