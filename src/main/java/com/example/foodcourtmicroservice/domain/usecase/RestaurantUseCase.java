package com.example.foodcourtmicroservice.domain.usecase;

import com.example.foodcourtmicroservice.configuration.Constants;
import com.example.foodcourtmicroservice.domain.api.IRestaurantExternalServicePort;
import com.example.foodcourtmicroservice.domain.api.IRestaurantServicePort;
import com.example.foodcourtmicroservice.domain.exception.UserMustBeOwnerException;
import com.example.foodcourtmicroservice.domain.exception.UserNotExistException;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import com.example.foodcourtmicroservice.domain.model.UserModel;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IUserFeignClientPort;

import java.util.List;

public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private  final IRestaurantExternalServicePort userFeignClient;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IRestaurantExternalServicePort resFeignClient){
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userFeignClient = resFeignClient;
    }
    @Override
    public void saveRestaurant(Restaurant restaurant) {

        String roleActual = userFeignClient.getRoleFromToken(Token.getToken());
        ValidateAuthorization.validateRole(roleActual,Constants.ROLE_ADMIN);

        if(!userFeignClient.validateOwner(restaurant.getIdOwner())){
            throw new UserMustBeOwnerException(Constants.USER_PERMISSION_DENIED);
        }

        this.restaurantPersistencePort.saveRestaurant(restaurant);
    }

    @Override
    public boolean existsUserById(Long idOwner) {
        return false;
    }

    @Override
    public UserModel getUserById(Long idOwner) {
        return null;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantPersistencePort.getAllRestaurants();
    }

    @Override
    public List<Restaurant> getRestaurantsWithPagination(Integer page, Integer size) {
        return restaurantPersistencePort.getRestaurantsWithPagination(page,size);
    }


}
