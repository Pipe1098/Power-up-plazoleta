package com.example.foodcourtmicroservice.domain.api;


import com.example.foodcourtmicroservice.domain.model.OrderRequestModel;

public interface IOrderServicePort {

    void saveOrder(OrderRequestModel orderModel);


}
