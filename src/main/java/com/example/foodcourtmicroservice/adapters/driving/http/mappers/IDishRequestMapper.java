package com.example.foodcourtmicroservice.adapters.driving.http.mappers;

import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.DishRequestDto;
import com.example.foodcourtmicroservice.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishRequestMapper {

    @Mapping(target = "idRestaurant.id", source = "idRestaurant")
    @Mapping(target = "idCategory.id", source = "idCategory")
    Dish toDish(DishRequestDto dishRequestDto);

}
