package com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories;


import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<CategoryEntity,Long> {

}
