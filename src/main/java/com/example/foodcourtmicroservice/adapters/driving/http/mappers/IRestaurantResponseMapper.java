package com.example.foodcourtmicroservice.adapters.driving.http.mappers;


import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IRestaurantResponseMapper {


    RestaurantResponseDto toResponse(Restaurant restaurantModel);

    List<RestaurantResponseDto> toResponseList(List<Restaurant> restaurantModelList);


}
