package com.example.foodcourtmicroservice.domain.spi;


import com.example.foodcourtmicroservice.domain.model.Category;

public interface ICategoryPersistencePort {

    Category getCategory(Long id);
}
