package com.example.foodcourtmicroservice.adapters.driving.http.handlers;


import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.OrderResponseDto;

import java.util.List;


public interface IOrderHandler {
    void saveOrder(OrderRequestDto orderRequest);
    List<OrderResponseDto> getAllOrdersWithPagination(Integer page, Integer size, String state);
    void takeOrderAndUpdateState(Long idOrder, String state);
    void notifyOrderReady(Long idOrder);
    void deliverOrder(Long idOrder, String pin);
}
