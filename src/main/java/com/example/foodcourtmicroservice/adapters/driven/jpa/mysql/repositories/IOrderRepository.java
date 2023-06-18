package com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories;


import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.example.foodcourtmicroservice.domain.model.OrderModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    boolean existsByIdClientAndState(Long id, String state);
    OrderEntity save(OrderEntity orderEntity);

    Boolean existsByIdAndState(Long idOrder, String statePending);

    Optional<OrderEntity> findById(Long idOrder);
    List<OrderEntity>  findAllByRestaurant_IdAndState(Long idRestaurant,String delivered);

    Page<OrderEntity> findByState(String state, Pageable pageable);
}
