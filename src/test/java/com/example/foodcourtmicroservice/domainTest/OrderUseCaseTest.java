package com.example.foodcourtmicroservice.domainTest;

import com.example.foodcourtmicroservice.adapters.driving.http.mappers.IOrderResponseMapper;
import com.example.foodcourtmicroservice.configuration.Constants;
import com.example.foodcourtmicroservice.domain.api.IRestaurantExternalServicePort;
import com.example.foodcourtmicroservice.domain.api.ITwilioFeignClientPort;
import com.example.foodcourtmicroservice.domain.exception.InvalidPinException;
import com.example.foodcourtmicroservice.domain.model.Dish;
import com.example.foodcourtmicroservice.domain.model.OrderDishModel;
import com.example.foodcourtmicroservice.domain.model.OrderDishRequestModel;
import com.example.foodcourtmicroservice.domain.model.OrderDishResponseModel;
import com.example.foodcourtmicroservice.domain.model.OrderModel;
import com.example.foodcourtmicroservice.domain.model.OrderRequestModel;
import com.example.foodcourtmicroservice.domain.model.OrderResponseModel;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import com.example.foodcourtmicroservice.domain.model.SmsMessageModel;
import com.example.foodcourtmicroservice.domain.model.UserModel;
import com.example.foodcourtmicroservice.domain.spi.IDishPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IOrderPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import com.example.foodcourtmicroservice.domain.usecase.OrderUseCase;
import com.example.foodcourtmicroservice.domain.usecase.Token;
import org.hibernate.boot.model.source.spi.PluralAttributeIndexSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @Mock
    private IOrderResponseMapper orderResponseMapper;
    private OrderUseCase orderUseCase;
    @Mock
    private ITwilioFeignClientPort twilioFeignClientPort;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        orderUseCase = new OrderUseCase(restaurantPersistencePort, dishPersistencePort, userFeignClientPort, orderResponseMapper, orderPersistencePort, twilioFeignClientPort);
    }

    @Test
    void saveOrder_ValidOrderRequestModel_SuccessfullySaved() {
        // Arrange
        OrderRequestModel orderRequestModel = createValidOrderRequestModel();
        Long idClient = 1L;
        Restaurant restaurant = createRestaurant();
        Dish dish = createDish();
        List<OrderDishRequestModel> orderDishes = createOrderDishes();
        Dish dishMock = mock(Dish.class);

        when(dishMock.getIdRestaurant()).thenReturn(restaurant);
        when(userFeignClientPort.getIdFromToken(Token.getToken())).thenReturn(String.valueOf(idClient));
        when(restaurantPersistencePort.getRestaurant(orderRequestModel.getResturantId())).thenReturn(restaurant);
        when(dishPersistencePort.getDish(anyLong())).thenReturn(dish);
        when(dishPersistencePort.getDish(anyLong())).thenReturn(dish);
        when(orderPersistencePort.existsByIdClientAndState(anyLong(), anyString())).thenReturn(false);
        when(dishMock.getActive()).thenReturn(true);

        // Act
        orderUseCase.saveOrder(orderRequestModel);

        // Assert
        verify(orderPersistencePort, times(1)).saveOrder(any(OrderModel.class));
        verify(orderPersistencePort, times(1)).saveOrderDish(anyList());
    }

    @Test
    void getAllOrdersWithPagination_ValidState_ReturnsOrderResponseModelList() {
        // Arrange
        Integer page = 0;
        Integer size = 10;
        String state = "PENDING";
        Pageable pageable = PageRequest.of(page, size);
        List<OrderModel> orderModels = createOrderModelList();
        List<OrderResponseModel> expectedResponseModels = createOrderResponseModelList(orderModels);

        when(dishPersistencePort.findByState(eq(state), eq(pageable))).thenReturn(new PageImpl<>(orderModels));
        when(dishPersistencePort.findAll(eq(pageable))).thenReturn(new PageImpl<>(orderModels));

        // Act
        List<OrderResponseModel> actualResponseModels = orderUseCase.getAllOrdersWithPagination(page, size, state);

        // Assert
        assertEquals(expectedResponseModels, actualResponseModels);
        verify(dishPersistencePort, times(1)).findByState(eq(state), eq(pageable));
    }

    @Test
    void takeOrderAndUpdateState_ValidIdOrderAndState_SuccessfullyUpdated() {
        // Arrange
        Long idOrder = 1L;
        String state = Constants.STATE_IN_PREPARATION;
        Long idEmployee = 2L;
        Long idRestaurant = 3L;
        Token.setToken("valid_token");
        String role = Constants.EMPLOYEE_ROLE;
        Restaurant restaurantEmployee = createRestaurant(idRestaurant);
        OrderModel orderModel = createOrderModel(idOrder, idRestaurant);

        when(userFeignClientPort.getIdFromToken(Token.getToken())).thenReturn(String.valueOf(idEmployee));
        when(userFeignClientPort.getIdRestaurantFromToken(Token.getToken())).thenReturn(String.valueOf(idRestaurant));
        when(userFeignClientPort.getRoleFromToken(Token.getToken())).thenReturn(role);
        when(orderPersistencePort.existsByIdAndState(idOrder, Constants.STATE_PENDING)).thenReturn(true);
        when(orderPersistencePort.getOrderById(idOrder)).thenReturn(orderModel);
        when(orderPersistencePort.saveOrder(orderModel)).thenReturn(orderModel);
        when(orderPersistencePort.saveOrder(any(OrderModel.class))).thenReturn(orderModel);
        when(restaurantPersistencePort.getRestaurant(idRestaurant)).thenReturn(restaurantEmployee);

        // Act
        orderUseCase.takeOrderAndUpdateState(idOrder, state);

        // Assert
        verify(userFeignClientPort, times(1)).getIdFromToken(Token.getToken());
        verify(userFeignClientPort, times(1)).getIdRestaurantFromToken(Token.getToken());
        verify(userFeignClientPort, times(1)).getRoleFromToken(Token.getToken());
        verify(orderPersistencePort, times(1)).existsByIdAndState(idOrder, Constants.STATE_PENDING);
        verify(orderPersistencePort, times(1)).getOrderById(idOrder);
        verify(restaurantPersistencePort, times(1)).getRestaurant(idRestaurant);
        verify(orderPersistencePort, times(1)).saveOrder(orderModel);
    }

    @Test
    void notifyOrderReady_OrderExists_UpdateOrderStatusAndSendNotification() {
        // Arrange
        Long idOrder = 1L;
        String state = Constants.STATE_IN_PREPARATION;
        Long idEmployee = 2L;
        Long idRestaurant = 3L;
        Token.setToken("valid_token");
        OrderModel orderModel = createOrderModel(idOrder, idRestaurant);
        UserModel userModel = new UserModel("sdsd", "gsg", "dsfsdf", "3002569874", "123456897", LocalDate.now());

        when(userFeignClientPort.getIdFromToken(Token.getToken())).thenReturn(String.valueOf(idEmployee));
        when(orderPersistencePort.existsByIdAndState(idOrder, Constants.STATE_IN_PREPARATION)).thenReturn(true);
        when(userFeignClientPort.getIdRestaurantFromToken(Token.getToken())).thenReturn(String.valueOf(idRestaurant));
        when(orderPersistencePort.getOrderById(idOrder)).thenReturn(orderModel);
        when(userFeignClientPort.getUserById(any())).thenReturn(userModel);
        orderModel.setId(idOrder);
        orderModel.setState("READY");
        String message = "Good day, Mr./Ms." + "your order is now ready for pickup.\nRemember to show the following PIN " + 123456 + " to receive your order.";
        String phone = "+573002217505";
        SmsMessageModel smsMessageModel = new SmsMessageModel(phone, message);

        // Act
        orderUseCase.notifyOrderReady(idOrder);

        // Assert
        verify(orderPersistencePort, times(1)).getOrderById(idOrder);
        verify(orderPersistencePort, times(1)).saveOrder(orderModel);
        verify(twilioFeignClientPort, times(1)).sendSmsMessage(smsMessageModel);
    }

    @Test
    void deliverOrder_ValidOrderAndPin_OrderDeliveredSuccessfully() {
        // Arrange
        Long idOrder = 1L;
        String pin = "1234";
        OrderModel orderModel = new OrderModel();
        orderModel.setId(idOrder);
        orderModel.setState(Constants.STATE_READY);
        orderModel.setPin(pin);

        when(orderPersistencePort.getOrderById(idOrder)).thenReturn(orderModel);

        // Act
        orderUseCase.deliverOrder(idOrder, pin);

        // Assert
        assertEquals(Constants.STATE_DELIVERED, orderModel.getState());
        verify(orderPersistencePort, times(1)).saveOrder(orderModel);
    }

    @Test
    void deliverOrder_InvalidPin_ExceptionThrown() {
        // Arrange
        Long idOrder = 1L;
        String pin = "1234";
        OrderModel orderModel = new OrderModel();
        orderModel.setId(idOrder);
        orderModel.setState(Constants.STATE_READY);
        orderModel.setPin("5678");

        when(orderPersistencePort.getOrderById(idOrder)).thenReturn(orderModel);

        // Act & Assert
        assertThrows(InvalidPinException.class, () -> orderUseCase.deliverOrder(idOrder, pin));
        verify(orderPersistencePort, never()).saveOrder(any(OrderModel.class));
    }

    @Test
    void cancelOrder_ValidIdOrder_CancelsOrderSuccessfully() {
        // Arrange
        Long idOrder = 1L;
        String token = "valid_token";
        Token.setToken("valid_token");
        Long idClient = 1L;
        OrderModel orderModel = new OrderModel();
        orderModel.setId(idOrder);
        orderModel.setState(Constants.STATE_PENDING);
        orderModel.setIdClient(idClient);

        when(userFeignClientPort.getIdFromToken(token)).thenReturn(String.valueOf(idClient));
        when(orderPersistencePort.getOrderById(idOrder)).thenReturn(orderModel);
        when(orderPersistencePort.existsByIdAndState(idOrder, Constants.STATE_IN_PREPARATION)).thenReturn(false);
        when(orderPersistencePort.existsByIdAndState(idOrder, Constants.STATE_READY)).thenReturn(false);
        when(orderPersistencePort.existsByIdAndState(idOrder, Constants.STATE_PENDING)).thenReturn(true);
        when(orderPersistencePort.saveOrder(orderModel)).thenReturn(orderModel);

        // Act
        orderUseCase.cancelOrder(idOrder);

        // Assert
        verify(userFeignClientPort).getIdFromToken(token);
        verify(orderPersistencePort).getOrderById(idOrder);
        verify(orderPersistencePort).existsByIdAndState(idOrder, Constants.STATE_IN_PREPARATION);
        verify(orderPersistencePort).existsByIdAndState(idOrder, Constants.STATE_READY);
        verify(orderPersistencePort).existsByIdAndState(idOrder, Constants.STATE_PENDING);
        verify(orderPersistencePort).saveOrder(orderModel);
        assertEquals(Constants.STATE_CANCELED, orderModel.getState());
    }

    private Restaurant createRestaurant(Long id) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        return restaurant;
    }

    private OrderModel createOrderModel(Long id, Long restaurantId) {
        OrderModel orderModel = new OrderModel();
        orderModel.setId(id);
        orderModel.setState(Constants.STATE_PENDING);
        Restaurant restaurant = createRestaurant(restaurantId);
        orderModel.setRestaurant(restaurant);
        return orderModel;
    }

    private List<OrderModel> createOrderModelList() {
        List<OrderModel> orderModels = new ArrayList<>();

        OrderModel orderModel1 = new OrderModel();
        orderModel1.setId(1L);
        orderModel1.setIdClient(1L);
        orderModel1.setDate(LocalDate.now());
        orderModel1.setState("PENDING");
        orderModel1.setIdEmployee(1L);
        orderModel1.setRestaurant(createRestaurant(1L));
        orderModels.add(orderModel1);

        OrderModel orderModel2 = new OrderModel();
        orderModel2.setId(2L);
        orderModel2.setIdClient(2L);
        orderModel2.setDate(LocalDate.now());
        orderModel2.setState("PENDING");
        orderModel2.setIdEmployee(2L);
        orderModel2.setRestaurant(createRestaurant(2L));
        orderModels.add(orderModel2);

        return orderModels;
    }

    private List<OrderResponseModel> createOrderResponseModelList(List<OrderModel> orderModels) {
        List<OrderResponseModel> orderResponseModels = new ArrayList<>();

        orderResponseModels = orderResponseMapper.toResponseModelList(orderModels);

        return orderResponseModels;
    }

    private List<OrderDishResponseModel> createOrderDishResponseModelList(List<OrderDishModel> orderDishModels) {
        List<OrderDishResponseModel> orderDishResponseModels = new ArrayList<>();

        // Create OrderDishResponseModel instances based on the given OrderDishModel instances
        for (OrderDishModel orderDishModel : orderDishModels) {
            OrderDishResponseModel orderDishResponseModel = new OrderDishResponseModel();
            orderDishResponseModel.setId(orderDishModel.getId());
            orderDishResponseModel.setName(orderDishModel.getDish().getname());
            orderDishResponseModel.setPrice(orderDishModel.getDish().getPrice());
            orderDishResponseModel.setDescription(orderDishModel.getDish().getDescription());
            orderDishResponseModel.setUrlImage(orderDishModel.getDish().getUrlImage());
            orderDishResponseModel.setCategoryId(orderDishModel.getDish().getIdCategory());
            orderDishResponseModel.setAmount(orderDishModel.getAmount());
            orderDishResponseModels.add(orderDishResponseModel);
        }

        return orderDishResponseModels;
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

