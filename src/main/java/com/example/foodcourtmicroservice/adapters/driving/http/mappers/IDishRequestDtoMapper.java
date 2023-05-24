package com.example.foodcourtmicroservice.adapters.driving.http.mappers;

import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.example.foodcourtmicroservice.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy =  ReportingPolicy.IGNORE)

public interface IDishRequestDtoMapper {

    @Mapping(source = "idCategory", target = "idCategoryAux")
    @Mapping(source = "idRestaurant", target = "idRestaurantAux")
    @Mapping(target = "idRestaurant", ignore = true)
    @Mapping(target = "idCategory", ignore = true)
    Dish toDish(DishRequestDto dishRequestDto);
}
