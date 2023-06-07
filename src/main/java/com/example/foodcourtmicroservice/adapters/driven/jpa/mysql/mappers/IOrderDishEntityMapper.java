package com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers;


import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import com.example.foodcourtmicroservice.domain.model.OrderDishModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderDishEntityMapper {
    OrderDishEntity toEntity(OrderDishModel orderDishModel);
    OrderDishModel toOrderModel(OrderDishEntity orderDishEntity);
}
