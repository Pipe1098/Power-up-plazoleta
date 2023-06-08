package com.example.foodcourtmicroservice.configuration;


import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter.CategoryMysqlAdapter;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter.DishMysqlAdapter;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter.OrderMysqlAdapter;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter.RestaurantMysqlAdapter;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.ICategoryEntityMapper;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IDishEntityMapper;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IOrderDishEntityMapper;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IOrderEntityMapper;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories.ICategoryRepository;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories.IDishRepository;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories.IOrderDishRepository;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories.IOrderRepository;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.example.foodcourtmicroservice.adapters.driving.http.feigns.FeignClientRestaurantAdapter;
import com.example.foodcourtmicroservice.adapters.driving.http.feigns.RestaurantFeignClient;
import com.example.foodcourtmicroservice.domain.api.IAuthServicePort;
import com.example.foodcourtmicroservice.domain.api.IDishServicePort;
import com.example.foodcourtmicroservice.domain.api.IOrderServicePort;
import com.example.foodcourtmicroservice.domain.api.IRestaurantExternalServicePort;
import com.example.foodcourtmicroservice.domain.api.IRestaurantServicePort;
import com.example.foodcourtmicroservice.domain.spi.ICategoryPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IDishPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IOrderPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import com.example.foodcourtmicroservice.domain.usecase.AuthUseCase;
import com.example.foodcourtmicroservice.domain.usecase.DishUseCase;
import com.example.foodcourtmicroservice.domain.usecase.OrderUseCase;
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
    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderDishRepository orderDishRepository;
    private final IOrderDishEntityMapper orderDishEntityMapper;


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
        return new DishMysqlAdapter(dishRepository,dishEntityMapper,restaurantRepository);
    }
    @Bean
    public ICategoryPersistencePort categoryPersistencePort(){
        return new CategoryMysqlAdapter(categoryRepository,categoryEntityMapper);
    }

    @Bean
    public IOrderServicePort orderServicePort(){
        return new OrderUseCase(restaurantPersistencePort(),dishPersistencePort(),feignServicePort(),orderPersistencePort());
    }
    @Bean
    public IOrderPersistencePort orderPersistencePort(){
        return new OrderMysqlAdapter(orderRepository,orderEntityMapper,orderDishRepository,orderDishEntityMapper);
    }
}
