package com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories;


import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    boolean existsByIdClientAndState(Long id, String state);
    OrderEntity save(OrderEntity orderEntity);

    Boolean existsByIdAndState(Long idOrder, String pendiente);

    Optional<OrderEntity> findById(Long id);
}
