package com.example.foodcourtmicroservice.domain.api;


import com.example.foodcourtmicroservice.domain.model.EmployeeRanking;
import com.example.foodcourtmicroservice.domain.model.OrderRequestModel;
import com.example.foodcourtmicroservice.domain.model.OrderResponseModel;
import java.util.List;

public interface IOrderServicePort {

    void saveOrder(OrderRequestModel orderModel);

    List<OrderResponseModel> getAllOrdersWithPagination(Integer page, Integer size, String state);

    void takeOrderAndUpdateState(Long idOrder, String state);

    void notifyOrderReady(Long idOrder);

    void deliverOrder(Long idOrder, String pin);

    void cancelOrder(Long idOrder);

    String getLogs(Long idOrder);

    List<EmployeeRanking> getEmployeeRankingByRestaurant(Long idRestaurant);
}
