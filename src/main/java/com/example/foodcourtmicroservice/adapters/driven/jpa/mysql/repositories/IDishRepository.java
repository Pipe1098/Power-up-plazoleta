package com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories;


import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {

    Page<DishEntity> findAllByIdRestaurant(Long idRestaurant, Pageable pageable);

    Page<DishEntity> findAllByIdRestaurantAndIdCategory(Long idRestaurant, String categoria, Pageable pageable);

    Page<DishEntity> findAllByIdRestaurantId(Long idRestaurant, Pageable pageable);

    Page<DishEntity> findAllByIdRestaurantIdAndIdCategoryId(Long idRestaurant, Long category, Pageable pageable);
}
