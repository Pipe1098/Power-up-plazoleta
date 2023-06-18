package com.example.foodcourtmicroservice.domainTest;


import com.example.foodcourtmicroservice.adapters.driving.http.mappers.IOrderResponseMapper;
import com.example.foodcourtmicroservice.configuration.Constants;
import com.example.foodcourtmicroservice.domain.api.IRestaurantExternalServicePort;
import com.example.foodcourtmicroservice.domain.api.ITwilioFeignServicePort;
import com.example.foodcourtmicroservice.domain.api.TrazabilityFeignServicePort;
import com.example.foodcourtmicroservice.domain.model.Dish;
import com.example.foodcourtmicroservice.domain.model.EmployeeRanking;
import com.example.foodcourtmicroservice.domain.model.LogModel;
import com.example.foodcourtmicroservice.domain.model.OrderDishRequestModel;
import com.example.foodcourtmicroservice.domain.model.OrderModel;
import com.example.foodcourtmicroservice.domain.model.OrderRequestModel;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import com.example.foodcourtmicroservice.domain.model.SmsMessageModel;
import com.example.foodcourtmicroservice.domain.model.UserModel;
import com.example.foodcourtmicroservice.domain.spi.IDishPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IOrderPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import com.example.foodcourtmicroservice.domain.usecase.OrderUseCase;
import com.example.foodcourtmicroservice.domain.usecase.Token;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class OrderUseCaseTest {
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;
    @Mock
    private IDishPersistencePort dishPersistencePort;
    @Mock
    private IRestaurantExternalServicePort userFeignClientPort;
    @Mock
    private ITwilioFeignServicePort twilioFeignClientPort;
    @Mock
    private TrazabilityFeignServicePort trazabilityFeignServicePort;
    @Mock
    private IOrderPersistencePort orderPersistencePort;
    @Mock
    private OrderUseCase orderUseCase;
    @Mock
    private IOrderResponseMapper orderResponseMapper;


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
        Token.setToken("abc123");
        Dish dishMock = mock(Dish.class);
        when(dishMock.getIdRestaurant()).thenReturn(restaurant);
        when(userFeignClientPort.getIdFromToken(Token.getToken())).thenReturn(String.valueOf(idClient));
        when(restaurantPersistencePort.getRestaurant(orderRequestModel.getResturantId())).thenReturn(restaurant);
        when(dishPersistencePort.getDish(any(Long.class))).thenReturn(dish);
        when( orderPersistencePort.existsByIdClientAndState(anyLong(), anyString())).thenReturn(false);
        when(dishMock.getActive()).thenReturn(true);

        // Act
        orderUseCase.saveOrder(orderRequestModel);

        // Assert
        verify(orderPersistencePort, times(1)).saveOrder(any(OrderModel.class));
        verify(orderPersistencePort, times(1)).saveOrderDish(anyList());
    }
    @Test
    public void testTakeOrderAndUpdateState() {

        String state = Constants.STATE_IN_PREPARATION;
        Long idEmployee = 1L;
        Long idRestaurant = 123L;
        String role = Constants.EMPLOYEE_ROLE;
        Restaurant restaurantEmployee = new Restaurant(1L,"PRUEBA","CR43-96","3006598745","URL1","3698547","325874");
        OrderModel orderModel = new OrderModel(1L, 1L, LocalDate.now(), Constants.STATE_PENDING, null, restaurantEmployee);
        Long idOrder = orderModel.getId();
        Token.setToken("abc123");

        when(userFeignClientPort.getIdFromToken(any())).thenReturn(idEmployee.toString());
        when(userFeignClientPort.getIdRestaurantFromToken(any())).thenReturn(idRestaurant.toString());
        when(restaurantPersistencePort.getRestaurant(any(Long.class))).thenReturn(restaurantEmployee);
        when(userFeignClientPort.getRoleFromToken(any())).thenReturn(role);
        when(orderPersistencePort.existsByIdAndState(idOrder, Constants.STATE_PENDING)).thenReturn(true);
        when(orderPersistencePort.getOrderById(idOrder)).thenReturn(orderModel);


        orderUseCase.takeOrderAndUpdateState(idOrder, state);


        verify(userFeignClientPort).getIdFromToken(any());
        verify(userFeignClientPort).getIdRestaurantFromToken(any());
        verify(userFeignClientPort).getRoleFromToken(any());
        verify(orderPersistencePort).existsByIdAndState(idOrder, Constants.STATE_PENDING);
        verify(orderPersistencePort).getOrderById(idOrder);
        verify(orderPersistencePort).saveOrder(orderModel);

        ArgumentCaptor<LogModel> logModelCaptor = ArgumentCaptor.forClass(LogModel.class);
        verify(trazabilityFeignServicePort).saveLog(logModelCaptor.capture());

        LogModel capturedLogModel = logModelCaptor.getValue();
        assertEquals(idOrder, capturedLogModel.getIdOrder());
        assertNotNull(capturedLogModel.getInPreparation());
    }
    @Test
    public void testGetEmployeeRankingByRestaurant() {
        // Act
        Long idRestaurant = 1234L;
        List<OrderModel> deliveredOrders = new ArrayList<>();
        deliveredOrders.add(new OrderModel(1L, 1L, LocalDate.now(), "delivered", 1L, new Restaurant()));
        deliveredOrders.add(new OrderModel(2L, 2L, LocalDate.now(), "delivered", 2L, new Restaurant()));
        deliveredOrders.add(new OrderModel(3L, 1L, LocalDate.now(), "delivered", 2L, new Restaurant()));
        when(orderPersistencePort.getOrdersByStateAndRestaurant(Constants.STATE_DELIVERED, idRestaurant))
                .thenReturn(deliveredOrders);
        when(userFeignClientPort.getMailFromToken(Token.getToken())).thenReturn("example@gmail.com");
        when(trazabilityFeignServicePort.totalTime(anyLong())).thenReturn(10L);

        // Arrange
        List<EmployeeRanking> employeeRankingList = orderUseCase.getEmployeeRankingByRestaurant(idRestaurant);

        // Assert
        assertNotNull(employeeRankingList);
        assertEquals(2, employeeRankingList.size());
        assertEquals(1L, employeeRankingList.get(0).getEmployeeId());
        assertEquals(10L, employeeRankingList.get(0).getAverageTime());
        assertEquals(2L, employeeRankingList.get(1).getEmployeeId());
        assertEquals(10L, employeeRankingList.get(1).getAverageTime());
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

    @Test
    public void testNotifyOrderReady() {

        Long idOrder = 1234L;
        Long idRestaurant = 123L;
        String state = Constants.STATE_IN_PREPARATION;
        String clientName = "John Doe";
        String pin = "1234";
        String phone = "+573002217505";
        String expectedMessage = "Good day, Mr./Ms. John Doe, your order is now ready for pickup.\nRemember to show the following PIN 1234 to receive your order.";

        OrderModel orderModel = new OrderModel(1L, 1L, LocalDate.now(), state, null, new Restaurant());
        UserModel userModel = new UserModel();
        userModel.setName(clientName);

        when(orderPersistencePort.existsByIdAndState(idOrder, Constants.STATE_IN_PREPARATION)).thenReturn(true);
        when(userFeignClientPort.getIdRestaurantFromToken(any())).thenReturn(idRestaurant.toString());
        when(orderPersistencePort.getOrderById(idOrder)).thenReturn(orderModel);
        when(userFeignClientPort.getUserById(any())).thenReturn(userModel);
        when(orderUseCase.generatePin(any())).thenReturn(pin);


        orderUseCase.notifyOrderReady(idOrder);


        verify(orderPersistencePort).existsByIdAndState(idOrder, Constants.STATE_IN_PREPARATION);
        verify(userFeignClientPort).getIdRestaurantFromToken(any());
        verify(orderPersistencePort).getOrderById(idOrder);
        verify(orderPersistencePort).saveOrder(orderModel);
        verify(userFeignClientPort).getUserById(any());
        verify(orderUseCase).generatePin(any());
        verify(twilioFeignClientPort).sendSmsMessage(new SmsMessageModel(phone, expectedMessage));

        ArgumentCaptor<LogModel> logModelCaptor = ArgumentCaptor.forClass(LogModel.class);
        verify(trazabilityFeignServicePort).saveLog(logModelCaptor.capture());

        LogModel capturedLogModel = logModelCaptor.getValue();
        assertEquals(idOrder, capturedLogModel.getIdOrder());
        assertNotNull(capturedLogModel.getReady());
    }

    @Test
    public void testCancelOrder() {
        Long orderId = 1234L;
        OrderModel orderModel = new OrderModel();
        orderModel.setId(orderId);
        orderModel.setIdClient(1l);
        Token.setToken("abc123");

        when(orderPersistencePort.getOrderById(orderId)).thenReturn(orderModel);
        when(userFeignClientPort.getIdFromToken(any())).thenReturn("1");
        when(orderPersistencePort.existsByIdAndState(orderId, Constants.STATE_PENDING)).thenReturn(true);
        when(orderPersistencePort.existsByIdAndState(orderId, Constants.STATE_READY)).thenReturn(false);

        // Act
        orderUseCase.cancelOrder(orderId);

        // Assert
        assertEquals(Constants.STATE_CANCELED, orderModel.getState());
        verify(orderPersistencePort, times(1)).saveOrder(orderModel);
    }


    private Restaurant createRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Restaurant Test");


        return restaurant;
    }

    @Test
    public void testDeliverOrder_WhenValidOrderAndPin_ShouldUpdateOrderStateAndSaveLog() {
        // Arrange
        Long idOrder = 1234L;
        String pin = "1234";

        OrderModel orderModel = new OrderModel();
        orderModel.setId(idOrder);
        orderModel.setState(Constants.STATE_READY);
        orderModel.setPin(pin);

        LocalDateTime currentDateTime = LocalDateTime.now();

        when(orderPersistencePort.getOrderById(idOrder)).thenReturn(orderModel);

        // Act
        orderUseCase.deliverOrder(idOrder, pin);

        // Assert
        verify(orderPersistencePort).getOrderById(idOrder);
        verify(orderPersistencePort).saveOrder(orderModel);

        LogModel expectedLog = new LogModel();
        expectedLog.setIdOrder(idOrder);
        expectedLog.setDelivered(currentDateTime);

        verify(trazabilityFeignServicePort).saveLog(expectedLog);
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

