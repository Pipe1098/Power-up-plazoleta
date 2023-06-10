package com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter;

import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.exceptions.NitAlredyRegistredException;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.example.foodcourtmicroservice.configuration.Constants;
import com.example.foodcourtmicroservice.domain.exception.NoDataFoundException;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RestaurantMysqlAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;


    @Override
    public void saveRestaurant(Restaurant restaurant) {
        if(restaurantRepository.findRestaurantEntityByNit(restaurant.getNit()).isPresent()){
            throw new NitAlredyRegistredException(Constants.NIT_ALREADY_REGISTERED);
        }
        RestaurantEntity restaurantEntity = restaurantEntityMapper.toEntity(restaurant);
        restaurantRepository.save(restaurantEntity);
    }

    @Override
    public Restaurant getRestaurant(Long idRestaurant) {
        Optional<RestaurantEntity> restaurantEntity= restaurantRepository.findById(idRestaurant);
       return restaurantEntityMapper.toRestaurant(restaurantEntity.get());

    }

    @Override
    public Restaurant getRestaurantByIdOwnwer(Long id) {
        return null;
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        List<RestaurantEntity> restaurantEntityList = (List<RestaurantEntity>) restaurantRepository.findAll();
        if(restaurantEntityList.isEmpty()){
            throw  new NoDataFoundException(Constants.RESTAURANT_NOT_FOUND);
        }
        return restaurantEntityMapper.toRestaurantList(restaurantEntityList);
    }

    @Override
    public List<Restaurant> getRestaurantsWithPagination(Integer page, Integer size) {
        PageRequest pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "name"));
        Page<RestaurantEntity> restaurantPage = restaurantRepository.findAll(pageable);

        List<RestaurantEntity> restaurantEntityList = restaurantPage.getContent();
        System.out.println(restaurantEntityList);
        if(restaurantEntityList.isEmpty()){
            throw new NoDataFoundException(Constants.RESTAURANT_NOT_FOUND);
        }
        return restaurantEntityMapper.toRestaurantList(restaurantEntityList);
    }

}
