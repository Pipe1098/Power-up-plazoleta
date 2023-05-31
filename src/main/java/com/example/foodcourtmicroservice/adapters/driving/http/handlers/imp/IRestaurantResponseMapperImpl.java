package com.example.foodcourtmicroservice.adapters.driving.http.handlers.imp;


import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.RestaurantResponseDto;
import com.example.foodcourtmicroservice.adapters.driving.http.mappers.IRestaurantResponseMapper;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import org.springframework.stereotype.Component;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.List;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-19T20:32:49-0500",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.jar, environment: Java 17.0.7 (Amazon.com Inc.)"
)
@Component
public class IRestaurantResponseMapperImpl implements IRestaurantResponseMapper {

    @Override
    public RestaurantResponseDto toResponse(Restaurant restaurant) {
        if ( restaurant == null ) {
            return null;
        }

        RestaurantResponseDto restaurantResponseDto = new RestaurantResponseDto();

        restaurantResponseDto.setId( restaurant.getId() );
        restaurantResponseDto.setName(restaurant.getName());
        restaurantResponseDto.setNit( restaurant.getNit() );
        restaurantResponseDto.setAdress( restaurant.getDirection() );
        restaurantResponseDto.setPhone( restaurant.getPhone() );
        restaurantResponseDto.setUrlLogo( restaurant.getUrlLogotype());
        restaurantResponseDto.setIdOwner( restaurant.getIdOwner() );

        return restaurantResponseDto;
    }

    @Override
    public List<RestaurantResponseDto> toResponseList(List<Restaurant> restaurantList) {
        if ( restaurantList == null ) {
            return null;
        }

        List<RestaurantResponseDto> list = new ArrayList<RestaurantResponseDto>( restaurantList.size() );
        for ( Restaurant restaurant : restaurantList ) {
            list.add( toResponse( restaurant ) );
        }

        return list;
    }




}
