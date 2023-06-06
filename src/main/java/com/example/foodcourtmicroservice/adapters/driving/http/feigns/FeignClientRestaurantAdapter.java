package com.example.foodcourtmicroservice.adapters.driving.http.feigns;

import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.RestaurantRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.exceptions.UserNotPermissionException;
import com.example.foodcourtmicroservice.adapters.driving.http.mappers.IRestaurantRequestMapper;
import com.example.foodcourtmicroservice.configuration.Constants;
import com.example.foodcourtmicroservice.domain.api.IRestaurantExternalServicePort;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import com.example.foodcourtmicroservice.domain.model.UserModel;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;


public class FeignClientRestaurantAdapter implements IRestaurantExternalServicePort {

    private  RestaurantFeignClient restaurantFeignClient;
    private  IRestaurantPersistencePort restaurantPersistencePort;
    private  IRestaurantRequestMapper restaurantRequestMapper;

    public FeignClientRestaurantAdapter(RestaurantFeignClient restaurantFeignClient, IRestaurantPersistencePort restaurantPersistencePort, IRestaurantRequestMapper restaurantRequestMapper) {
        this.restaurantFeignClient = restaurantFeignClient;
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.restaurantRequestMapper = restaurantRequestMapper;
    }

    public FeignClientRestaurantAdapter(RestaurantFeignClient restaurantFeignClient) {
        this.restaurantFeignClient = restaurantFeignClient;
    }

/*    @Override
    public void saveRestaurant(RestaurantRequestDto restaurantRequestDto) {
        if(restaurantFeignClient.getUser(restaurantRequestDto.getIdOwner()).getIdRole().getName().equals(Constants.ROLE_ADMIN)){
            Restaurant restaurant = restaurantRequestMapper.toRestaurant(restaurantRequestDto);
            restaurantPersistencePort.saveRestaurant(restaurant);
        }   else{
            throw new UserNotPermissionException(Constants.USER_PERMISSION_DENIED);
        }
    }*/

    @Override
    public String getRoleFromToken(String token) {
        return restaurantFeignClient.roleUser(token);
    }

    @Override
    public String getIdOwnerFromToken(String token) {
        return restaurantFeignClient.idUser(token);
    }

    @Override
    public boolean existsUserById(Long idOwner) {
        return false;
    }

    @Override
    public boolean validateOwner(String idOwner) {
        return restaurantFeignClient.validateOwner(idOwner);
    }

    @Override
    public UserModel getUserById(String idOwner) {
        return null;
    }
}
