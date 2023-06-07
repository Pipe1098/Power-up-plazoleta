package com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories;


import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    boolean existsByIdClientAndState(Long id, String state);
    OrderEntity save(OrderEntity orderEntity);

}
