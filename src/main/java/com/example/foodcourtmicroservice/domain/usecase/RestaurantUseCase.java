package com.example.foodcourtmicroservice.domain.usecase;

import com.example.foodcourtmicroservice.domain.api.IRestaurantServicePort;
import com.example.foodcourtmicroservice.domain.exception.OwnerMustOnlyOwnARestaurantException;
import com.example.foodcourtmicroservice.domain.exception.UserMustBeOwnerException;
import com.example.foodcourtmicroservice.domain.exception.UserNotExistException;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import com.example.foodcourtmicroservice.domain.model.UserModel;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IUserFeignClientPort;

public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private  final IUserFeignClientPort userFeignClient;
    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserFeignClientPort userFeignClient){ this.restaurantPersistencePort = restaurantPersistencePort;
        this.userFeignClient = userFeignClient;
    }
    @Override
    public void saveRestaurant(Restaurant restaurant) {

        boolean existUser = userFeignClient.existsUserById(restaurant.getIdOwner());

        if (!existUser) throw new UserNotExistException();
        UserModel user = userFeignClient.getUserById(restaurant.getIdOwner());

        if (user.getRole().getId() != 2) throw new UserMustBeOwnerException();

        restaurantPersistencePort.saveRestaurant(restaurant);
    }




}
