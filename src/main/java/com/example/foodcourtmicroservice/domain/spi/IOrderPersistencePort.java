package com.example.foodcourtmicroservice.domain.spi;


import com.example.foodcourtmicroservice.domain.model.OrderDishModel;
import com.example.foodcourtmicroservice.domain.model.OrderModel;

import java.util.List;

public interface IOrderPersistencePort {
    OrderModel saveOrder(OrderModel orderModel);

    void saveOrderDish(List<OrderDishModel> orderDishModels);

    Boolean existsByIdClientAndState(Long id, String estado);

    Boolean existsByIdAndState(Long idOrder, String statePending);

    OrderModel getOrderById(Long idOrder);
}
