package com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter;

import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.exceptions.UserNotPermissionException;
import com.example.foodcourtmicroservice.adapters.driving.http.mappers.IRestaurantRequestMapper;
import com.example.foodcourtmicroservice.configuration.Constants;
import com.example.foodcourtmicroservice.domain.api.IRestaurantExternalServicePort;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;


public class FeignClientRestaurantAdapter implements IRestaurantExternalServicePort {
    private final RestaurantFeignClient restaurantFeignClient;
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IRestaurantRequestMapper restaurantRequestMapper;

    public FeignClientRestaurantAdapter(RestaurantFeignClient restaurantFeignClient, IRestaurantPersistencePort restaurantPersistencePort, IRestaurantRequestMapper restaurantRequestMapper) {
        this.restaurantFeignClient = restaurantFeignClient;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.restaurantRequestMapper = restaurantRequestMapper;
    }

    @Override
    public void saveRestaurant(RestaurantRequestDto restaurantRequestDto) {
        if(restaurantFeignClient.getUserByDni(restaurantRequestDto.getIdOwner()).getIdRole().getName().equals(Constants.PROVIDER_DESCRPTION)){
            Restaurant restaurant = restaurantRequestMapper.toRestaurant(restaurantRequestDto);
            restaurantPersistencePort.saveRestaurant(restaurant);
        }   else{
            throw new UserNotPermissionException(Constants.USER_PERMISSION_DENIED);
        }
    }
}
