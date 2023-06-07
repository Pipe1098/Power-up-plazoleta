package com.example.foodcourtmicroservice.adapters.driving.http.mappers;


import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.example.foodcourtmicroservice.domain.model.OrderModel;
import com.example.foodcourtmicroservice.domain.model.OrderRequestModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IOrderRequestMapper {

    OrderModel toOrderModel(OrderRequestDto orderRequestDto);

    OrderRequestModel toOrderRequestModel(OrderRequestDto orderRequestDto);


}
