package com.example.foodcourtmicroservice.domain.api;


import com.example.foodcourtmicroservice.domain.model.OrderRequestModel;
import com.example.foodcourtmicroservice.domain.model.OrderResponseModel;

import java.util.List;

public interface IOrderServicePort {

    void saveOrder(OrderRequestModel orderModel);

    List<OrderResponseModel> getAllOrdersWithPagination(Integer page, Integer size, String state);

    void takeOrderAndUpdateState(Long idOrder, String state);

    void notifyOrderReady(Long idOrder);
}
