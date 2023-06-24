package com.example.foodcourtmicroservice.adapters.driving.http.feigns;


import com.example.foodcourtmicroservice.adapters.driving.http.mappers.IRestaurantRequestMapper;
import com.example.foodcourtmicroservice.domain.api.IRestaurantExternalServicePort;
import com.example.foodcourtmicroservice.domain.model.UserModel;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;



public class FeignClientRestaurantAdapter implements IRestaurantExternalServicePort {

    private  RestaurantFeignClient restaurantFeignClient;



    public FeignClientRestaurantAdapter(RestaurantFeignClient restaurantFeignClient) {
        this.restaurantFeignClient = restaurantFeignClient;
    }


    @Override
    public String getRoleFromToken(String token) {
        return restaurantFeignClient.roleUser(token);
    }

    @Override
    public String getIdFromToken(String token) {
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

    @Override
    public String getIdRestaurantFromToken(String token) {
        return restaurantFeignClient.idRestaurantUser(token);
    }

    @Override
    public String getMailFromToken(String token) {
        return restaurantFeignClient.getmailToken(token);
    }

    @Override
    public String getUserNameById(String idUser) {
        return restaurantFeignClient.getUserNameById(idUser);
    }
}
