package com.example.foodcourtmicroservice.adapters.driving.http.feigns;

import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.exceptions.UserNotPermissionException;
import com.example.foodcourtmicroservice.adapters.driving.http.mappers.IRestaurantRequestMapper;
import com.example.foodcourtmicroservice.configuration.Constants;
import com.example.foodcourtmicroservice.domain.api.IRestaurantExternalServicePort;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeignClientRestaurantAdapter implements IRestaurantExternalServicePort {

    private final RestaurantFeignClient restaurantFeignClient;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;


    @Override
    public void saveRestaurant(RestaurantRequestDto restaurantRequestDto) {
        if(restaurantFeignClient.getUserByDni(restaurantRequestDto.getIdOwner()).getIdRole().getName().equals(Constants.ROLE_ADMIN)){
            Restaurant restaurant = restaurantRequestMapper.toRestaurant(restaurantRequestDto);
            restaurantPersistencePort.saveRestaurant(restaurant);
        }   else{
            throw new UserNotPermissionException(Constants.USER_PERMISSION_DENIED);
        }
    }

    @Override
    public String getRolFromToken(String token) {
        return null;
    }

    @Override
    public String getIdOwnerFromToken(String token) {
        return null;
    }
}
