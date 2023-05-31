package com.example.foodcourtmicroservice.configuration;


import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter.CategoryMysqlAdapter;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter.DishMysqlAdapter;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter.RestaurantMysqlAdapter;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.ICategoryEntityMapper;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.example.foodcourtmicroservice.adapters.driving.http.feigns.FeignClientRestaurantAdapter;
import com.example.foodcourtmicroservice.adapters.driving.http.feigns.RestaurantFeignClient;
import com.example.foodcourtmicroservice.domain.api.IAuthServicePort;
import com.example.foodcourtmicroservice.domain.api.IDishServicePort;
import com.example.foodcourtmicroservice.domain.api.IRestaurantExternalServicePort;
import com.example.foodcourtmicroservice.domain.api.IRestaurantServicePort;
import com.example.foodcourtmicroservice.domain.spi.ICategoryPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IDishPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import com.example.foodcourtmicroservice.domain.usecase.AuthUseCase;
import com.example.foodcourtmicroservice.domain.usecase.DishUseCase;
import com.example.foodcourtmicroservice.domain.usecase.RestaurantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IDishRepository dishRepository;
    private final IDishEntityMapper dishEntityMapper;
    private final RestaurantFeignClient restaurantFeignClient;
    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;


    @Bean
    public IRestaurantServicePort restauranteServicePort(){
        return new RestaurantUseCase(restaurantPersistencePort(), feignServicePort());
    }
    @Bean
    public IAuthServicePort authServicePort(){
        return new AuthUseCase();
    }
    @Bean
    public IRestaurantExternalServicePort feignServicePort(){
        return new FeignClientRestaurantAdapter(restaurantFeignClient);
    }
    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantMysqlAdapter(restaurantRepository,restaurantEntityMapper);
    }
    @Bean
    public IDishServicePort dishServicePort(){
        return new DishUseCase(dishPersistencePort(),restaurantPersistencePort(),categoryPersistencePort(),feignServicePort());
    }
    @Bean
    public IDishPersistencePort dishPersistencePort(){
        return new DishMysqlAdapter(dishRepository,dishEntityMapper);
    }
    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){
        return new CategoryMysqlAdapter(categoryRepository,categoryEntityMapper);
    }
}
