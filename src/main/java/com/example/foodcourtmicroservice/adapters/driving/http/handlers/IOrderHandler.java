package com.example.foodcourtmicroservice.adapters.driving.http.handlers;


import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.OrderRequestDto;


public interface IOrderHandler {
    void saveOrder(OrderRequestDto orderRequest);
}
