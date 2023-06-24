package com.example.foodcourtmicroservice.adapters.driving.http.handlers;


import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.LogsResponseDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.example.foodcourtmicroservice.domain.model.EmployeeRanking;

import java.util.List;


public interface IOrderHandler {
    void saveOrder(OrderRequestDto orderRequest);
    List<OrderResponseDto> getAllOrdersWithPagination(Integer page, Integer size, String state);
    void takeOrderAndUpdateState(Long idOrder, String state);
    void notifyOrderReady(Long idOrder);
    void deliverOrder(Long idOrder, String pin);
    void cancelOrder(Long idOrder);
    String getLogs(Long idOrder);
    List<EmployeeRanking> getEfficiency(Long idRestaurant);
}
