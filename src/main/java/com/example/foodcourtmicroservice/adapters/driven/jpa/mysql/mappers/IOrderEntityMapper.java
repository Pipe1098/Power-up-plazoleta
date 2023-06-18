package com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers;


import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.example.foodcourtmicroservice.domain.model.OrderModel;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderEntityMapper {
    OrderEntity toEntity(OrderModel orderModel);
    OrderModel toOrderModel(OrderEntity orderEntity);
    List<OrderModel> toOrderModelList(List<OrderEntity> orderEntity);
}
