package com.example.foodcourtmicroservice.domainTest;

import com.example.foodcourtmicroservice.domain.exception.OwnerMustOnlyOwnARestaurantException;
import com.example.foodcourtmicroservice.domain.exception.UserMustBeOwnerException;
import com.example.foodcourtmicroservice.domain.exception.UserNotExistException;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import com.example.foodcourtmicroservice.domain.model.RoleModel;
import com.example.foodcourtmicroservice.domain.model.UserModel;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IUserFeignClientPort;
import com.example.foodcourtmicroservice.domain.usecase.RestaurantUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;


    @ExtendWith(SpringExtension.class)
    public class RestaurantTest {

        @InjectMocks
        RestaurantUseCase restaurantUseCase;

        @Mock
        IRestaurantPersistencePort restaurantPersistencePort;

        @Mock
        IUserFeignClientPort userFeignClient;

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

            UserModel userModel = new UserModel();
            userModel.setRole(new RoleModel(2L, "OWNER ", "Owner"));

            Mockito.when(userFeignClient.existsUserById(Long.parseLong(restaurant.getIdOwner()))).thenReturn(true);
            Mockito.when(userFeignClient.getUserById(Long.parseLong(restaurant.getIdOwner()))).thenReturn(userModel);
            Mockito.when(restaurantPersistencePort.getRestaurantByIdOwnwer(userModel.getId())).thenReturn(null);

            restaurantUseCase.saveRestaurant(restaurant);

            Mockito.verify(restaurantPersistencePort).saveRestaurant(Mockito.any(Restaurant.class));
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

            Mockito.when(userFeignClient.existsUserById(Long.parseLong(restaurant.getIdOwner()))).thenReturn(false);
            Assertions.assertThrows(UserNotExistException.class, () -> restaurantUseCase.saveRestaurant(restaurant));
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


            UserModel userModel = new UserModel();
            userModel.setRole(new RoleModel(3L, "EMPLEADO", "Empleado"));


            Mockito.when(userFeignClient.existsUserById(Long.parseLong(restaurant.getIdOwner()))).thenReturn(true);
            Mockito.when(userFeignClient.getUserById(Long.parseLong(restaurant.getIdOwner()))).thenReturn(userModel);

            Assertions.assertThrows(UserMustBeOwnerException.class, () -> restaurantUseCase.saveRestaurant(restaurant));
        }

        @Test
        void saveRestaurantWithOwnerAlreadyOwningARestaurant() {
            Restaurant restaurant = new Restaurant();
            restaurant.setId(1L);
            restaurant.setName("Presto");
            restaurant.setNit("5894144123");
            restaurant.setAdress("cr 45");
            restaurant.setPhone("3002217575");
            restaurant.setUrlLogotype("http:/miplazoleta");
            restaurant.setIdOwner("2");

            UserModel userModel = new UserModel();
            userModel.setRole(new RoleModel(2L, "OWNER ", "Owner"));

            Restaurant existingRestaurantModel = new Restaurant();

            Mockito.when(userFeignClient.existsUserById(Long.parseLong(restaurant.getIdOwner()))).thenReturn(true);
            Mockito.when(userFeignClient.getUserById(Long.parseLong(restaurant.getIdOwner()))).thenReturn(userModel);
            Mockito.when(restaurantPersistencePort.getRestaurantByIdOwnwer((userModel.getId()))).thenReturn(existingRestaurantModel);

            Assertions.assertThrows(OwnerMustOnlyOwnARestaurantException.class, () -> restaurantUseCase.saveRestaurant(restaurant));
        }

    }

