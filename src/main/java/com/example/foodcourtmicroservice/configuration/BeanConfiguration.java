package com.example.foodcourtmicroservice.configuration;

import com.example.foodcourtmicroservice.adapters.driving.http.feigns.FeignClientRestaurantAdapter;
import com.example.foodcourtmicroservice.adapters.driving.http.feigns.RestaurantFeignClient;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.adapter.RestaurantMysqlAdapter;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.mappers.IRestaurantEntityMapper;
import com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.repositories.IRestaurantRepository;
import com.example.foodcourtmicroservice.adapters.driving.http.mappers.IRestaurantRequestMapper;
import com.example.foodcourtmicroservice.domain.api.IRestaurantExternalServicePort;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IUserFeignClientPort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;

    private final IUserFeignClientPort userFeignClient;

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantMysqlAdapter(restaurantRepository, restaurantEntityMapper);
    }
//    @Bean
//    public IRestaurantServicePort restaurantServicePort(){ return new RestaurantUseCase(restaurantPersistencePort(), userFeignClient);}

    @Bean
    public IRestaurantExternalServicePort restaurantExternalServicePort(RestaurantFeignClient restaurantFeignClient,
                                                                        IRestaurantPersistencePort restaurantPersistencePort,
                                                                        IRestaurantRequestMapper restaurantRequestMapper){
        return new FeignClientRestaurantAdapter(restaurantFeignClient, restaurantPersistencePort, restaurantRequestMapper);
    }

}
