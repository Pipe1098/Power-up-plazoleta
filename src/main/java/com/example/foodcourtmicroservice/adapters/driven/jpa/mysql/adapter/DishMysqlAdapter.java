package com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter;


import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.example.foodcourtmicroservice.configuration.Constants;
import com.example.foodcourtmicroservice.domain.exception.DishNoFoundException;
import com.example.foodcourtmicroservice.domain.model.Dish;
import com.example.foodcourtmicroservice.domain.spi.IDishPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DishMysqlAdapter implements IDishPersistencePort {
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;

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
}
