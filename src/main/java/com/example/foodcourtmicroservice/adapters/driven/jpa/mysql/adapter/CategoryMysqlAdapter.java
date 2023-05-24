package com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter;

import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.ICategoryEntityMapper;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.example.foodcourtmicroservice.domain.exception.CategoryNotFoundException;
import com.example.foodcourtmicroservice.domain.model.Category;
import com.example.foodcourtmicroservice.domain.spi.ICategoryPersistencePort;
import com.example.foodcourtmicroservice.configuration.Constants;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CategoryMysqlAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;


    @Override
    public Category getCategory(Long id) {
        Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
        if(categoryEntity.isEmpty()){
            throw new CategoryNotFoundException(Constants.CATEGORY_NOT_FOUND);
        }
        return categoryEntityMapper.toCategory(categoryEntity.get());
    }
}
