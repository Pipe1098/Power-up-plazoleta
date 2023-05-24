package com.example.foodcourtmicroservice.domain.usecase;

import com.example.foodcourtmicroservice.domain.api.IRestaurantExternalServicePort;
import com.example.foodcourtmicroservice.domain.api.IRestaurantServicePort;
import com.example.foodcourtmicroservice.domain.exception.UserMustBeOwnerException;
import com.example.foodcourtmicroservice.domain.exception.UserNotExistException;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import com.example.foodcourtmicroservice.domain.model.UserModel;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IUserFeignClientPort;

public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private  final IRestaurantExternalServicePort IuserFeignClient;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IRestaurantExternalServicePort resFeignClient){
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.IuserFeignClient = resFeignClient;
    }
    @Override
    public void saveRestaurant(Restaurant restaurant) {

        boolean existUser = IuserFeignClient.existsUserById(restaurant.getIdOwner());

        if (!existUser) throw new UserNotExistException();
        UserModel user = IuserFeignClient.getUserById(restaurant.getIdOwner());

        if (user.getRole().getId() != 2) throw new UserMustBeOwnerException();

        restaurantPersistencePort.saveRestaurant(restaurant);
    }

    @Override
    public boolean existsUserById(Long idOwner) {
        return false;
    }

    @Override
    public UserModel getUserById(Long idOwner) {
        return null;
    }


}
