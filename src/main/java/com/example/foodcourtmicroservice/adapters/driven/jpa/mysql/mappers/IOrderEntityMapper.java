package com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers;


import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.example.foodcourtmicroservice.domain.model.OrderModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IOrderEntityMapper {
    OrderEntity toEntity(OrderModel orderModel);
    OrderModel toOrderModel(OrderEntity orderEntity);
}
