package com.example.foodcourtmicroservice.domainTest;

import com.example.foodcourtmicroservice.domain.api.IRestaurantExternalServicePort;
import com.example.foodcourtmicroservice.domain.exception.OwnerMustOnlyOwnARestaurantException;
import com.example.foodcourtmicroservice.domain.exception.UserMustBeOwnerException;
import com.example.foodcourtmicroservice.domain.exception.UserNotExistException;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import com.example.foodcourtmicroservice.domain.model.RoleModel;
import com.example.foodcourtmicroservice.domain.model.UserModel;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IUserFeignClientPort;
import com.example.foodcourtmicroservice.domain.usecase.RestaurantUseCase;
import com.example.foodcourtmicroservice.domain.usecase.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;


@ExtendWith(SpringExtension.class)
public class RestaurantTest {

    @InjectMocks
    RestaurantUseCase restaurantUseCase;

    @Mock
    IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    IUserFeignClientPort userFeignClient;
    @Mock
    IRestaurantExternalServicePort restaurantExternalServicePortFeignClient;

    @Test
    void mustSaveARestaurant() {
        Restaurant restaurant = new Restaurant();

        restaurant.setId(1L);
        restaurant.setName("Presto");
        restaurant.setNit("5894144123");
        restaurant.setAdress("cr 45");
        restaurant.setPhone("3002217575");
        restaurant.setUrlLogotype("http:/miplazoleta");
        restaurant.setIdOwner("234");
        Token.setToken("exampe_Token");
        UserModel userModel = new UserModel("Juan ", "Perez", "perez@gmail.com", "3002569874", "369857", LocalDate.now());

        Mockito.when(restaurantExternalServicePortFeignClient.getRoleFromToken(any())).thenReturn("ROLE_ADMIN");
        Mockito.when(restaurantExternalServicePortFeignClient.validateOwner(restaurant.getIdOwner())).thenReturn(true);

        restaurantUseCase.saveRestaurant(restaurant);

        Mockito.verify(restaurantPersistencePort).saveRestaurant(any(Restaurant.class));
    }

    @Test
    void saveRestaurantWithNonExistingUser() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Presto");
        restaurant.setNit("5894144123");
        restaurant.setAdress("cr 45");
        restaurant.setPhone("3002217575");
        restaurant.setUrlLogotype("http:/miplazoleta");
        restaurant.setIdOwner("2");

        Mockito.when(restaurantExternalServicePortFeignClient.getRoleFromToken(any())).thenReturn("ROLE_ADMIN");
        Mockito.when(restaurantExternalServicePortFeignClient.validateOwner(restaurant.getIdOwner())).thenReturn(false);
        Assertions.assertThrows(UserMustBeOwnerException.class, () -> restaurantUseCase.saveRestaurant(restaurant));
    }

    @Test
    void saveRestaurantWithNonOwnerUser() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Presto");
        restaurant.setNit("5894144123");
        restaurant.setAdress("cr 45");
        restaurant.setPhone("3002217575");
        restaurant.setUrlLogotype("http:/miplazoleta");
        restaurant.setIdOwner("3");


        UserModel userModel = new UserModel("Juan ", "Perez", "perez@gmail.com", "3002569874", "369857", LocalDate.now());

        Mockito.when(userFeignClient.existsUserById(Long.parseLong(restaurant.getIdOwner()))).thenReturn(true);
        Mockito.when(userFeignClient.getUserById(Long.parseLong(restaurant.getIdOwner()))).thenReturn(userModel);

        Assertions.assertThrows(UserMustBeOwnerException.class, () -> restaurantUseCase.saveRestaurant(restaurant));
    }


}

