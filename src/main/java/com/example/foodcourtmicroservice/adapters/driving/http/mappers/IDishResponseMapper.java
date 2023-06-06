package com.example.foodcourtmicroservice.adapters.driving.http.mappers;


import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.DishResponseDto;
import com.example.foodcourtmicroservice.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IDishResponseMapper {

    //@Mapping(target = "restauranteId", source = "restauranteId.id")
    //@Mapping(target = "categoriaId", source = "categoriaId.id")
    DishResponseDto toResponse(Dish dishModel);

    List<DishResponseDto> toResponseList(List<Dish> dishList);

}
