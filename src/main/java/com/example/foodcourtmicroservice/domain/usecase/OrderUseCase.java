package com.example.foodcourtmicroservice.domain.usecase;

import com.example.foodcourtmicroservice.configuration.Constants;
import com.example.foodcourtmicroservice.domain.api.IOrderServicePort;
import com.example.foodcourtmicroservice.domain.api.IRestaurantExternalServicePort;
import com.example.foodcourtmicroservice.domain.exception.ClientHasAnOrderException;
import com.example.foodcourtmicroservice.domain.exception.DishIdRestaurantIsNotEqualsOrderException;
import com.example.foodcourtmicroservice.domain.exception.DishIsInactiveException;
import com.example.foodcourtmicroservice.domain.exception.DishNotExistException;
import com.example.foodcourtmicroservice.domain.exception.NoDataFoundException;
import com.example.foodcourtmicroservice.domain.exception.RestaurantNoFoundException;
import com.example.foodcourtmicroservice.domain.exception.UserNotAuthenticatedException;
import com.example.foodcourtmicroservice.domain.model.Dish;
import com.example.foodcourtmicroservice.domain.model.OrderDishModel;
import com.example.foodcourtmicroservice.domain.model.OrderDishRequestModel;
import com.example.foodcourtmicroservice.domain.model.OrderModel;
import com.example.foodcourtmicroservice.domain.model.OrderRequestModel;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import com.example.foodcourtmicroservice.domain.spi.IDishPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IOrderPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static java.lang.Long.parseLong;


public class OrderUseCase implements IOrderServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantExternalServicePort userFeignClientPort;
    private final IOrderPersistencePort orderPersistencePort;

    public OrderUseCase(IRestaurantPersistencePort restaurantPersistencePort, IDishPersistencePort dishPersistencePort, IRestaurantExternalServicePort userFeignClientPort, IOrderPersistencePort orderPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
        this.userFeignClientPort = userFeignClientPort;
        this.orderPersistencePort = orderPersistencePort;
    }


@Override
public void saveOrder(OrderRequestModel orderRequestModel) {

    validateUserAuthentication();

    Long idClient = parseLong(userFeignClientPort.getIdFromToken(Token.getToken()));

    validateClientOrderStatus(idClient);

    Restaurant restaurant = getRestaurantById(orderRequestModel.getResturantId());

    List<OrderDishRequestModel> orderDishes = orderRequestModel.getDishes();
    validateOrderDishes(orderDishes);

    OrderModel orderModel = createOrderModel(idClient, restaurant);
    validateDishes(orderDishes, orderModel.getRestaurant());

    OrderModel order = saveOrder(orderModel);
    saveOrderDishes(order, orderDishes);
}

    private void validateUserAuthentication() {
        if (Token.getToken() == null) {
            throw new UserNotAuthenticatedException(Constants.USER_NOT_AUTHENTICATED);
        }
    }

    private void validateClientOrderStatus(Long idClient) {
        List<String> states = List.of(Constants.STATE_PENDING, Constants.STATE_IN_PREPARATION, Constants.STATE_READY);
        if (orderPersistencePort.existsByIdClientAndState(idClient, states.get(0))||
                orderPersistencePort.existsByIdClientAndState(idClient, states.get(1)) ||
                orderPersistencePort.existsByIdClientAndState(idClient, states.get(2))) {
            throw new ClientHasAnOrderException(Constants.CLIENT_HAS_AN_ORDER);
        }
    }

    private Restaurant getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantPersistencePort.getRestaurant(restaurantId);
        if (restaurant == null) {
            throw new RestaurantNoFoundException(Constants.RESTAURANT_NOT_FOUND);
        }
        return restaurant;
    }

    private void validateOrderDishes(List<OrderDishRequestModel> orderDishes) {
        if (orderDishes.isEmpty()) {
            throw new NoDataFoundException(Constants.NOT_DATA_FOUND_DISH);
        }
    }

    private OrderModel createOrderModel(Long idClient, Restaurant restaurant) {
        return new OrderModel(-1L, idClient, LocalDate.now(), Constants.STATE_PENDING, null, restaurant);
    }

    private void validateDishes(List<OrderDishRequestModel> orderDishes, Restaurant restaurant) {
        for (OrderDishRequestModel dishRequestModel : orderDishes) {
            Dish dish = dishPersistencePort.getDish(dishRequestModel.getIdDish());
            if (dish == null) {
                throw new DishNotExistException(Constants.DISH_NOT_EXIST);
            }
            if (!dish.getIdRestaurant().equals(restaurant.getId())) {
                throw new DishIdRestaurantIsNotEqualsOrderException(Constants.DISH_IDRESTAURANT_DIFERENT);
            }
            if (!dish.getActive()) {
                throw new DishIsInactiveException(Constants.DISH_NOT_ACTIVE);
            }
        }
    }

    private OrderModel saveOrder(OrderModel orderModel) {
        return orderPersistencePort.saveOrder(orderModel);
    }

    private void saveOrderDishes(OrderModel order, List<OrderDishRequestModel> orderDishes) {
        List<OrderDishModel> orderDishesList = new ArrayList<>();
        for (OrderDishRequestModel dishRequestModel : orderDishes) {
            Dish dishModel = dishPersistencePort.getDish(dishRequestModel.getIdDish());
            OrderDishModel orderDish = new OrderDishModel(-1L, order, dishModel, String.valueOf(dishRequestModel.getNumberDishes()));
            orderDishesList.add(orderDish);
        }
        orderPersistencePort.saveOrderDish(orderDishesList);
    }
}
