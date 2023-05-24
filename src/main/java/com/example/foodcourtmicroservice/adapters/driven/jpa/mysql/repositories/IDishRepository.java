package com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories;


import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {

}
