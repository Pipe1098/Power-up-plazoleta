package com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers;

import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)

public interface IRestaurantEntityMapper {
    RestaurantEntity toEntity(Restaurant restaurant);
    Restaurant toRestaurant(RestaurantEntity restaurantEntity);
    List<Restaurant> toRestaurantList(List<RestaurantEntity> restaurantEntityList);
}
