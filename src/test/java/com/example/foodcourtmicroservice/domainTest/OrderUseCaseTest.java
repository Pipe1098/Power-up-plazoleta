package com.example.foodcourtmicroservice.domainTest;

import com.example.foodcourtmicroservice.domain.api.IRestaurantExternalServicePort;
import com.example.foodcourtmicroservice.domain.model.Dish;
import com.example.foodcourtmicroservice.domain.model.OrderDishRequestModel;
import com.example.foodcourtmicroservice.domain.model.OrderModel;
import com.example.foodcourtmicroservice.domain.model.OrderRequestModel;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import com.example.foodcourtmicroservice.domain.spi.IDishPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IOrderPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import com.example.foodcourtmicroservice.domain.usecase.OrderUseCase;
import com.example.foodcourtmicroservice.domain.usecase.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class OrderUseCaseTest {
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private IDishPersistencePort dishPersistencePort;
    @Mock
    private IRestaurantExternalServicePort userFeignClientPort;
    @Mock
    private IOrderPersistencePort orderPersistencePort;

    private OrderUseCase orderUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderUseCase = new OrderUseCase(restaurantPersistencePort, dishPersistencePort, userFeignClientPort, orderResponseMapper, orderPersistencePort, twilioFeignClientPort, trazabilityFeignServicePort);
    }

    @Test
    void saveOrder_ValidOrderRequestModel_SuccessfullySaved() {
        // Arrange
        OrderRequestModel orderRequestModel = createValidOrderRequestModel();
        Long idClient = 1L;
        Restaurant restaurant = createRestaurant();
        Dish dish = createDish();
        List<OrderDishRequestModel> orderDishes = createOrderDishes();
       // Token token = mock(Token.class);
        Dish dishMock = mock(Dish.class);

// Configurar el comportamiento del método getIdRestaurant()
        when(dishMock.getIdRestaurant()).thenReturn(restaurant);
        when(userFeignClientPort.getIdFromToken(Token.getToken())).thenReturn(String.valueOf(idClient));
        when(restaurantPersistencePort.getRestaurant(orderRequestModel.getResturantId())).thenReturn(restaurant);
        when(dishPersistencePort.getDish(anyLong())).thenReturn(dish);
        when(dishPersistencePort.getDish(anyLong())).thenReturn(dish);
        when( orderPersistencePort.existsByIdClientAndState(anyLong(), anyString())).thenReturn(false);
        when(dishMock.getActive()).thenReturn(true);

        // Act
        orderUseCase.saveOrder(orderRequestModel);

        // Assert
        verify(orderPersistencePort, times(1)).saveOrder(any(OrderModel.class));
        verify(orderPersistencePort, times(1)).saveOrderDish(anyList());
    }


    private OrderRequestModel createValidOrderRequestModel() {
        OrderRequestModel orderRequestModel = new OrderRequestModel();
        orderRequestModel.setResturantId(1L);

        List<OrderDishRequestModel> orderDishes = new ArrayList<>();
        OrderDishRequestModel orderDish1 = new OrderDishRequestModel();
        orderDish1.setIdDish(1L);
        orderDish1.setNumberDishes(2l);
        orderDishes.add(orderDish1);


        orderRequestModel.setDishes(orderDishes);

        return orderRequestModel;
    }

    private Restaurant createRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Restaurant Test");


        return restaurant;
    }

    private List<OrderDishRequestModel> createOrderDishes() {
        List<OrderDishRequestModel> orderDishes = new ArrayList<>();

        OrderDishRequestModel orderDish1 = new OrderDishRequestModel();
        orderDish1.setIdDish(1L);
        orderDish1.setNumberDishes(2l);
        orderDishes.add(orderDish1);

        OrderDishRequestModel orderDish2 = new OrderDishRequestModel();
        orderDish2.setIdDish(2L);
        orderDish2.setNumberDishes(1l);
        orderDishes.add(orderDish2);


        return orderDishes;
    }

    private Dish createDish() {
        Dish dish = new Dish();
        dish.setId(1L);
        dish.setname("Dish Test");
        dish.setIdRestaurant(createRestaurant());


        return dish;
    }
}

