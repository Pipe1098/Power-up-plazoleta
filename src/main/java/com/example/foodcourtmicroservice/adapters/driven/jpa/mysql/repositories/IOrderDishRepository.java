package com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories;

import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.OrderDishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IOrderDishRepository extends JpaRepository<OrderDishEntity, Long> {

    List<OrderDishEntity> findByOrder_Id(Long order_id);
}
