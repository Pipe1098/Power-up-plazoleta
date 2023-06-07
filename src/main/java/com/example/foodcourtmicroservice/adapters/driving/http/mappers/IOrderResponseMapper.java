package com.example.foodcourtmicroservice.adapters.driving.http.mappers;


import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.example.foodcourtmicroservice.domain.model.OrderModel;
import com.example.foodcourtmicroservice.domain.model.OrderResponseModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderResponseMapper {

    OrderResponseDto toResponse(OrderModel orderModel);
    List<OrderResponseDto> toResponseList(List<OrderModel> orderModels);
    List<OrderResponseDto> toOrderResponseList(List<OrderResponseModel> orderResponseModels);
}
