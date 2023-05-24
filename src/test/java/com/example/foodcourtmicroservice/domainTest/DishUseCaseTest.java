package com.example.foodcourtmicroservice.domainTest;


import com.example.foodcourtmicroservice.domain.api.IRestaurantExternalServicePort;
import com.example.foodcourtmicroservice.domain.exception.OwnerAnotherRestaurantException;
import com.example.foodcourtmicroservice.domain.exception.UserNoAutorizedException;
import com.example.foodcourtmicroservice.domain.model.Category;
import com.example.foodcourtmicroservice.domain.model.Dish;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import com.example.foodcourtmicroservice.domain.spi.ICategoryPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IDishPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import com.example.foodcourtmicroservice.domain.usecase.DishUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = DishUseCase.class)
@SpringBootTest
class DishUseCaseTest {
    @MockBean
    IDishPersistencePort dishPersistencePort;
    @MockBean
    IRestaurantPersistencePort restaurantPersistencePort;
    @MockBean
    ICategoryPersistencePort categoryPersistencePort;
    @MockBean
    IRestaurantExternalServicePort feignServicePort;
    @InjectMocks
    @Autowired
    DishUseCase dishUseCase;
    Dish dish;

    @BeforeEach
    void setDish(){
        dish = new Dish(
                1L,
                "Pizza hawaina",
                new Category(
                        2L,
                        "italian",
                        "description"
                ),
                2L,
                "pizza mediana",
                "25000",
                new Restaurant(
                        3L,
                        "Domines",
                        "123",
                        "local",
                        "urlLogo",
                        "3265874",
                        2L
                ),
                3L,
                "urlImage",
                true
        );
    }
    @Test
    void saveDish(){
        when(feignServicePort.getRolFromToken(any())).thenReturn("ROLE_OWNER ");
        when(restaurantPersistencePort.getRestaurant(any())).thenReturn(new Restaurant(
                3L,
                "name",
                "123",
                "local",
                "UrlLogo",
                "359874597",
                2L
        ));
        when(categoryPersistencePort.getCategory(any())).thenReturn(new Category(
                2L,
                "Italian",
                "pizza mediana"
        ));
        when(feignServicePort.getIdOwnerFromToken(any())).thenReturn("2");

        dishUseCase.saveDish(dish);

        verify(dishPersistencePort).saveDish(dish);
    }
    @Test
    void saveDishUserNoAutorized(){
        when(feignServicePort.getRolFromToken(any())).thenReturn("ROLE_CLIENT");
        when(restaurantPersistencePort.getRestaurant(any())).thenReturn(new Restaurant(
                3L,
                "name",
                "123",
                "local",
                "3024261812",
                "url",
                2L
        ));
        when(categoryPersistencePort.getCategory(any())).thenReturn(new Category(
                2L,
                "name ",
                "description"
        ));
        when(feignServicePort.getIdOwnerFromToken(any())).thenReturn("2");

        assertThrows(UserNoAutorizedException.class, () -> dishUseCase.saveDish(dish));
    }
    @Test
    void saveDishOwnerNoMatch(){
        when(feignServicePort.getRolFromToken(any())).thenReturn("ROLE_OWNER ");
        when(restaurantPersistencePort.getRestaurant(any())).thenReturn(new Restaurant(
                3L,
                "name",
                "123",
                "local",
                "3024261812",
                "url",
                6L
        ));
        when(categoryPersistencePort.getCategory(any())).thenReturn(new Category(
                2L,
                "name",
                "description"
        ));
        when(feignServicePort.getIdOwnerFromToken(any())).thenReturn("2");

        assertThrows(OwnerAnotherRestaurantException.class, () -> dishUseCase.saveDish(dish));
    }
    @Test
    void updateDish(){
        when(dishPersistencePort.getDish(any())).thenReturn(dish);
        when(feignServicePort.getIdOwnerFromToken(any())).thenReturn("2");
        when(feignServicePort.getRolFromToken(any())).thenReturn("ROLE_OWNER ");

        dishUseCase.updateDish(1L,"1000","dish updatesdd");

        verify(dishPersistencePort).updateDish(dish);
    }
    @Test
    void updateDishUserNoAutorized(){
        when(dishPersistencePort.getDish(any())).thenReturn(dish);
        when(feignServicePort.getIdOwnerFromToken(any())).thenReturn("2");
        when(feignServicePort.getRolFromToken(any())).thenReturn("ROLE_CLIENT");

        assertThrows(UserNoAutorizedException.class, () -> dishUseCase.updateDish(1L,"1000","dish updated"));
    }
    @Test
    void updateDishOwnerNoMatch(){
        when(dishPersistencePort.getDish(any())).thenReturn(dish);
        when(feignServicePort.getIdOwnerFromToken(any())).thenReturn("3");
        when(feignServicePort.getRolFromToken(any())).thenReturn("ROLE_OWNER ");

        assertThrows(OwnerAnotherRestaurantException.class, () -> dishUseCase.updateDish(2L,"1000","dish updated"));
    }

}
