package com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter;


import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IOrderDishEntityMapper;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IOrderEntityMapper;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories.IOrderDishRepository;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.example.foodcourtmicroservice.domain.model.OrderDishModel;
import com.example.foodcourtmicroservice.domain.model.OrderModel;
import com.example.foodcourtmicroservice.domain.spi.IOrderPersistencePort;
import lombok.RequiredArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
public class OrderMysqlAdapter implements IOrderPersistencePort {

    private final IOrderRepository orderRepository;

    private final IOrderEntityMapper orderEntityMapper;

    private final IOrderDishRepository orderDishRepository;

    private final IOrderDishEntityMapper orderDishEntityMapper;


    @Override
    public OrderModel saveOrder(OrderModel orderModel) {
        OrderEntity orderEntity= orderRepository.save(orderEntityMapper.toEntity(orderModel));
        return orderEntityMapper.toOrderModel(orderEntity);
    }

    @Override
    public void saveOrderDish(List<OrderDishModel> orderDishModels) {
        List<OrderDishEntity> orderDishEntities = new ArrayList<>();
        for (int i=0; i<orderDishModels.size();i++){
            orderDishEntities.add(orderDishEntityMapper.toEntity(orderDishModels.get(i)));
        }
       orderDishRepository.saveAll(orderDishEntities);
    }

    @Override
    public Boolean existsByIdClientAndState(Long id, String estado) {
        return orderRepository.existsByIdClientAndState(id, estado);
    }

    @Override
    public Boolean existsByIdAndState(Long idOrder, String pendiente) {
        return orderRepository.existsByIdAndState(idOrder, pendiente);
    }

    @Override
    public OrderModel getOrderById(Long id) {
        Optional<OrderEntity> orderEntityOptional =orderRepository.findById(id);
        OrderEntity orderEntity= orderEntityOptional.orElse(null);
        return orderEntityMapper.toOrderModel(orderEntity);
    }

}
