package com.example.foodcourtmicroservice.adapters.driving.http.handlers.imp;


import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.example.foodcourtmicroservice.adapters.driving.http.mappers.IOrderRequestMapper;
import com.example.foodcourtmicroservice.domain.api.IOrderServicePort;
import com.example.foodcourtmicroservice.domain.model.OrderRequestModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandlerImpl implements IOrderHandler {

    private final IOrderServicePort orderServicePort;

    private final IOrderRequestMapper orderRequestMapper;


    @Override
    public void saveOrder(OrderRequestDto orderRequest) {
        OrderRequestModel orderRequestModel= orderRequestMapper.toOrderRequestModel(orderRequest);
           orderServicePort.saveOrder(orderRequestModel);
    }

}
