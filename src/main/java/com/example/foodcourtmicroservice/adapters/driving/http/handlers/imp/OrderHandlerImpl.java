package com.example.foodcourtmicroservice.adapters.driving.http.handlers.imp;


import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.LogsResponseDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.example.foodcourtmicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.example.foodcourtmicroservice.adapters.driving.http.mappers.IOrderRequestMapper;
import com.example.foodcourtmicroservice.adapters.driving.http.mappers.IOrderResponseMapper;
import com.example.foodcourtmicroservice.domain.api.IOrderServicePort;
import com.example.foodcourtmicroservice.domain.model.EmployeeRanking;
import com.example.foodcourtmicroservice.domain.model.OrderRequestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandlerImpl implements IOrderHandler {

    private final IOrderServicePort orderServicePort;

    private final IOrderRequestMapper orderRequestMapper;
    private final IOrderResponseMapper orderResponseMapper;


    @Override
    public void saveOrder(OrderRequestDto orderRequest) {
        OrderRequestModel orderRequestModel= orderRequestMapper.toOrderRequestModel(orderRequest);
           orderServicePort.saveOrder(orderRequestModel);
    }

    @Override
    public List<OrderResponseDto> getAllOrdersWithPagination(Integer page, Integer size, String state) {
        return orderResponseMapper.toOrderResponseList(orderServicePort.getAllOrdersWithPagination(page,size,state));
    }

    @Override
    public void takeOrderAndUpdateState(Long idOrder, String state) {
        orderServicePort.takeOrderAndUpdateState(idOrder,state);
    }

    @Override
    public void notifyOrderReady(Long idOrder) {
        orderServicePort.notifyOrderReady(idOrder);
    }

    @Override
    public void deliverOrder(Long idOrder, String pin) {
        orderServicePort.deliverOrder(idOrder,pin);
    }

    @Override
    public void cancelOrder(Long idOrder) {
        orderServicePort.cancelOrder(idOrder);
    }

    @Override
    public String getLogs(Long idOrder) {
        return  orderServicePort.getLogs(idOrder);
    }

    @Override
    public List<EmployeeRanking> getEfficiency(Long idRestaurant) {
        return orderServicePort.getEmployeeRankingByRestaurant(idRestaurant);
    }

}
