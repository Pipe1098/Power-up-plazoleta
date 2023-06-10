package com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter;


import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.example.foodcourtmicroservice.configuration.Constants;
import com.example.foodcourtmicroservice.domain.exception.CategoryNotFoundException;
import com.example.foodcourtmicroservice.domain.exception.DishNoFoundException;
import com.example.foodcourtmicroservice.domain.exception.RestaurantNoFoundException;
import com.example.foodcourtmicroservice.domain.model.Dish;
import com.example.foodcourtmicroservice.domain.model.OrderModel;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import com.example.foodcourtmicroservice.domain.spi.IDishPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DishMysqlAdapter implements IDishPersistencePort {
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final IRestaurantRepository restaurantRepository;

    @Override
    public void saveDish(Dish dish) {
        dishRepository.save(dishEntityMapper.toEntity(dish));
    }

    @Override
    public void updateDish(Dish dish) {
        dishRepository.save(dishEntityMapper.toEntity(dish));
    }

    @Override
    public Dish getDish(Long id) {
        Optional<DishEntity> dishEntity = dishRepository.findById(id);
        if(dishEntity.isEmpty()){
            throw new DishNoFoundException(Constants.UNREGISTERED_DISH);
        }
        return dishEntityMapper.toDish(dishEntity.get());
    }

    @Override
    public List<Dish> findAllByRestaurantId(Long idRestaurant, Integer page, Integer size) {
        Optional<RestaurantEntity> restaurantOptional = restaurantRepository.findById(idRestaurant);
        if (restaurantOptional.isPresent()) {
            Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
            Page<DishEntity> dishPage = dishRepository.findAll(pageable);
            List<DishEntity> dishEntities = dishPage.getContent();
            System.out.println(dishEntities);
            return dishEntities.stream()
                    .map(dishEntityMapper::toDish)
                    .collect(Collectors.toList());
        } else {
            throw new RestaurantNoFoundException(Constants.RESTAURANT_NOT_FOUND);
        }
    }

    @Override
    public List<Dish> findAllByRestaurantIdAndCategory(Long category,Long idRestaurant, Integer page, Integer size) {
        Optional<RestaurantEntity> restaurantOptional = restaurantRepository.findById(idRestaurant);
        if (restaurantOptional.isPresent()) {
            Pageable pageable = PageRequest.of(page, size, Sort.by("idCategory"));
            Page<DishEntity> dishPage = dishRepository.findAllByIdRestaurantIdAndIdCategoryId(idRestaurant, category, pageable);
            List<DishEntity> dishEntities = dishPage.getContent();
            if (dishEntities.isEmpty()) {
                throw new CategoryNotFoundException(Constants.CATEGORY_NOT_FOUND);
            }
            return dishEntities.stream()
                    .map(dishEntityMapper::toDish)
                    .collect(Collectors.toList());
        } else {
            throw new RestaurantNoFoundException(Constants.RESTAURANT_NOT_FOUND);
        }
    }

    @Override
    public Page<OrderModel> findByState(String state, Pageable pageable) {
        return null;
    }

    @Override
    public Page<OrderModel> findAll(Pageable pageable) {
        return null;
    }

}




