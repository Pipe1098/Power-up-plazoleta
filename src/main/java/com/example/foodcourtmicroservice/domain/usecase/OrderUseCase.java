package com.example.foodcourtmicroservice.domain.usecase;

import com.example.foodcourtmicroservice.adapters.driving.http.mappers.IOrderResponseMapper;
import com.example.foodcourtmicroservice.configuration.Constants;
import com.example.foodcourtmicroservice.domain.api.IOrderServicePort;
import com.example.foodcourtmicroservice.domain.api.IRestaurantExternalServicePort;
import com.example.foodcourtmicroservice.domain.api.ITwilioFeignServicePort;
import com.example.foodcourtmicroservice.domain.api.TrazabilityFeignServicePort;
import com.example.foodcourtmicroservice.domain.exception.ClientAuthMustBeEqualsClientOrderException;
import com.example.foodcourtmicroservice.domain.exception.ClientHasAnOrderException;
import com.example.foodcourtmicroservice.domain.exception.DishIdRestaurantIsNotEqualsOrderException;
import com.example.foodcourtmicroservice.domain.exception.DishIsInactiveException;
import com.example.foodcourtmicroservice.domain.exception.DishNotExistException;
import com.example.foodcourtmicroservice.domain.exception.InvalidOrderStateException;
import com.example.foodcourtmicroservice.domain.exception.InvalidPinException;
import com.example.foodcourtmicroservice.domain.exception.NoCancelOrdersPreparationOrReadyException;
import com.example.foodcourtmicroservice.domain.exception.NoDataFoundException;
import com.example.foodcourtmicroservice.domain.exception.OnlyCancelOrderStatusPendingException;
import com.example.foodcourtmicroservice.domain.exception.OrderNotExistException;
import com.example.foodcourtmicroservice.domain.exception.OrderRestaurantMustBeEqualsEmployeeRestaurantException;
import com.example.foodcourtmicroservice.domain.exception.RestaurantNoFoundException;
import com.example.foodcourtmicroservice.domain.exception.UserNotAuthenticatedException;
import com.example.foodcourtmicroservice.domain.model.Dish;
import com.example.foodcourtmicroservice.domain.model.EmployeeRanking;
import com.example.foodcourtmicroservice.domain.model.LogModel;
import com.example.foodcourtmicroservice.domain.model.OrderDishModel;
import com.example.foodcourtmicroservice.domain.model.OrderDishRequestModel;
import com.example.foodcourtmicroservice.domain.model.OrderModel;
import com.example.foodcourtmicroservice.domain.model.OrderRequestModel;
import com.example.foodcourtmicroservice.domain.model.OrderResponseModel;
import com.example.foodcourtmicroservice.domain.model.Restaurant;
import com.example.foodcourtmicroservice.domain.model.SmsMessageModel;
import com.example.foodcourtmicroservice.domain.model.UserModel;
import com.example.foodcourtmicroservice.domain.spi.IDishPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IOrderPersistencePort;
import com.example.foodcourtmicroservice.domain.spi.IRestaurantPersistencePort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static java.lang.Long.parseLong;


public class OrderUseCase implements IOrderServicePort {

    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final IRestaurantExternalServicePort userFeignClientPort;
    private final IOrderResponseMapper orderResponseMapper;
    private final IOrderPersistencePort orderPersistencePort;
    private final ITwilioFeignServicePort twilioFeignClientPort;
    private final TrazabilityFeignServicePort trazabilityFeignServicePort;


    public OrderUseCase(IRestaurantPersistencePort restaurantPersistencePort, IDishPersistencePort dishPersistencePort, IRestaurantExternalServicePort userFeignClientPort, IOrderResponseMapper orderResponseMapper, IOrderPersistencePort orderPersistencePort, ITwilioFeignServicePort twilioFeignClientPort, TrazabilityFeignServicePort trazabilityFeignServicePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
        this.userFeignClientPort = userFeignClientPort;
        this.orderResponseMapper = orderResponseMapper;
        this.orderPersistencePort = orderPersistencePort;
        this.twilioFeignClientPort = twilioFeignClientPort;
        this.trazabilityFeignServicePort = trazabilityFeignServicePort;
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
        //validateDishes(orderDishes, orderModel.getRestaurant());

        OrderModel order = saveOrder(orderModel);
        saveOrderDishes(order, orderDishes);
        LogModel newLog = new LogModel();
        newLog.setIdOrder(orderModel.getId());
        newLog.setPending(LocalDateTime.now());
        trazabilityFeignServicePort.saveLog(newLog);
    }

    @Override
    public List<OrderResponseModel> getAllOrdersWithPagination(Integer page, Integer size, String state) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderModel> orderPage;
        if (state != null && !state.isEmpty()) {
            orderPage = dishPersistencePort.findByState(state, pageable);
        } else {
            orderPage = dishPersistencePort.findAll(pageable);
        }
        List<OrderModel> orderList = orderPage.getContent();

        return orderResponseMapper.toResponseModelList(orderList);
    }

    @Override
    public void takeOrderAndUpdateState(Long idOrder, String state) {
        validateUserAuthentication();

        Long idEmployee = parseLong(userFeignClientPort.getIdFromToken(Token.getToken()));
        Long idRestaurant = parseLong(userFeignClientPort.getIdRestaurantFromToken(Token.getToken()));
        String role = userFeignClientPort.getRoleFromToken(Token.getToken());

        Restaurant restaurantEmployee = getRestaurantById(idRestaurant);

        if (!role.equals(Constants.EMPLOYEE_ROLE)) {
            throw new UserNotAuthenticatedException(Constants.UNAUTHORIZED_USER);
        }

        if (!state.equals(Constants.STATE_IN_PREPARATION)) {
            throw new NoDataFoundException(Constants.STATE_NOT_VALID);
        }
        if (Boolean.FALSE.equals(orderPersistencePort.existsByIdAndState(idOrder, Constants.STATE_PENDING))) {
            throw new NoDataFoundException(Constants.ACTUAL_STATE_NOT_VALID);
        }

        OrderModel orderModel = orderPersistencePort.getOrderById(idOrder);
        if (orderModel == null) {
            throw new OrderNotExistException(Constants.ORDER_NOT_EXIST);
        }
        if (restaurantEmployee.getId() != orderModel.getRestaurant().getId()) {
            throw new OrderRestaurantMustBeEqualsEmployeeRestaurantException(Constants.IDEMPLOYEE_IDORDER_DIFERENT);
        }
        orderModel.setState(state);
        orderModel.setIdEmployee(idEmployee);
        orderPersistencePort.saveOrder(orderModel);
        LogModel newLog = new LogModel();
        newLog.setIdOrder(orderModel.getId());
        newLog.setInPreparation(LocalDateTime.now());

        trazabilityFeignServicePort.saveLog(newLog);
    }

    @Override
    public void notifyOrderReady(Long idOrder) {
        if (!orderPersistencePort.existsByIdAndState(idOrder, Constants.STATE_IN_PREPARATION)) {
            throw new NoDataFoundException("Order not found or not in preparation state.");
        }

        validateUserAuthentication();

        Long idRestaurant = parseLong(userFeignClientPort.getIdRestaurantFromToken(Token.getToken()));

        OrderModel orderModel = orderPersistencePort.getOrderById(idOrder);
        if (orderModel == null) {
            throw new OrderNotExistException(Constants.ORDER_NOT_EXIST);
        }

        Long idRestaurantOrder = orderModel.getRestaurant().getId();

        if (!idRestaurant.equals(idRestaurantOrder)) {
            throw new OrderRestaurantMustBeEqualsEmployeeRestaurantException("Order restaurant must be equal to employee restaurant.");
        }

        orderModel.setState(Constants.STATE_READY);
        orderPersistencePort.saveOrder(orderModel);

        UserModel userModel = userFeignClientPort.getUserById(orderModel.getIdClient().toString());
        String ClientName = userModel.getName();
        String pin = generatePin(userModel);


        String message = "Good day, Mr./Ms. " + ClientName + ", your order is now ready for pickup.\nRemember to show the following PIN " + pin + " to receive your order.";
        String phone = "+573002217505";

        SmsMessageModel smsMessageModel = new SmsMessageModel(phone, message);

        twilioFeignClientPort.sendSmsMessage(smsMessageModel);

        LogModel newLog = new LogModel();
        newLog.setIdOrder(orderModel.getId());
        newLog.setReady(LocalDateTime.now());
        trazabilityFeignServicePort.saveLog(newLog);
    }

    @Override
    public void deliverOrder(Long idOrder, String pin) {
        OrderModel orderModel = orderPersistencePort.getOrderById(idOrder);
        if (orderModel == null) {
            throw new OrderNotExistException(Constants.ORDER_NOT_EXIST);
        }

        if (!orderModel.getState().equals(Constants.STATE_READY)) {
            throw new InvalidOrderStateException("Only orders in 'READY' state can be delivered.");
        }

        if (!orderModel.getPin().equals(pin)) {
            throw new InvalidPinException("Invalid pin entered.");
        }

        orderModel.setState(Constants.STATE_DELIVERED);
        orderPersistencePort.saveOrder(orderModel);

        LogModel newLog = new LogModel();
        newLog.setIdOrder(orderModel.getId());
        newLog.setDelivered(LocalDateTime.now());
        trazabilityFeignServicePort.saveLog(newLog);
    }

    @Override
    public void cancelOrder(Long idOrder) {
        validateUserAuthentication();
        Long idClient = parseLong(userFeignClientPort.getIdFromToken(Token.getToken()));

        OrderModel orderModel = orderPersistencePort.getOrderById(idOrder);
        if (orderModel == null) {
            throw new OrderNotExistException(Constants.ORDER_NOT_EXIST);
        }
        Long idClientOrder = orderModel.getIdClient();

        if (!idClient.equals(idClientOrder)) {
            throw new ClientAuthMustBeEqualsClientOrderException("The authenticated client has to be the same client of the order");
        }

        if (orderPersistencePort.existsByIdAndState(idOrder, Constants.STATE_IN_PREPARATION)
                || orderPersistencePort.existsByIdAndState(idOrder, Constants.STATE_READY)) {
            SmsMessageModel smsMessageModel = new SmsMessageModel("+573002217505", "Sorry, your order is already being prepared and cannot be cancelled.");
            twilioFeignClientPort.sendSmsMessage(smsMessageModel);
            throw new NoCancelOrdersPreparationOrReadyException("Orders with status IN_PREPARATION or READY cannot be canceled");
        } else if (!orderPersistencePort.existsByIdAndState(idOrder, Constants.STATE_PENDING)) {
            throw new OnlyCancelOrderStatusPendingException("Only Orders with status PENDING  can be canceled");
        }

        orderModel.setState(Constants.STATE_CANCELED);
        orderPersistencePort.saveOrder(orderModel);
    }

    @Override
    public String getLogs(Long idOrder) {
        return trazabilityFeignServicePort.timeStates(idOrder).toString();
    }

    public List<EmployeeRanking> getEmployeeRankingByRestaurant(Long idRestaurant) {
        List<OrderModel> deliveredOrders = orderPersistencePort.getOrdersByStateAndRestaurant(Constants.STATE_DELIVERED, idRestaurant);
        Map<Long, Long> employeeTotalTimes = new HashMap<>();
        Map<Long, Integer> employeeOrderCounts = new HashMap<>();

        for (OrderModel order : deliveredOrders) {
            Long totalTime = trazabilityFeignServicePort.totalTime(order.getId());
            Long employeeId = order.getIdEmployee();


            if (employeeTotalTimes.containsKey(employeeId)) {
                Long currentTotalTime = employeeTotalTimes.get(employeeId);
                int orderCount = employeeOrderCounts.get(employeeId);
                employeeTotalTimes.put(employeeId, currentTotalTime + totalTime);
                employeeOrderCounts.put(employeeId, orderCount + 1);
            } else {
                employeeTotalTimes.put(employeeId, totalTime);
                employeeOrderCounts.put(employeeId, 1);
            }
        }

        List<EmployeeRanking> employeeRankingList = new ArrayList<>();

        for (Map.Entry<Long, Long> entry : employeeTotalTimes.entrySet()) {
            Long employeeId = entry.getKey();
            Long totalTime = entry.getValue();
            String employeeMail = userFeignClientPort.getMailFromToken(Token.getToken());
            int orderCount = employeeOrderCounts.get(employeeId);
            long averageTime = totalTime / orderCount; // Cálculo del promedio
            EmployeeRanking employeeRanking = new EmployeeRanking(employeeId, averageTime, employeeMail);
            employeeRankingList.add(employeeRanking);
        }

        employeeRankingList.sort(Comparator.comparingLong(EmployeeRanking::getAverageTime));

        return employeeRankingList;
    }

    public String generatePin(UserModel userModel) {

        String alphanumericCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        int pinLength = 6; // Longitud del PIN deseada

        StringBuilder pinBuilder = new StringBuilder();
        Random random = new Random();

        // Generar caracteres aleatorios hasta alcanzar la longitud del PIN
        for (int i = 0; i < pinLength; i++) {
            int randomIndex = random.nextInt(alphanumericCharacters.length());
            char randomChar = alphanumericCharacters.charAt(randomIndex);
            pinBuilder.append(randomChar);
        }

        String pin = pinBuilder.toString();
        userModel.setPin(pin);

        return pin;
    }

    public void validateUserAuthentication() {
        if (Token.getToken() == null) {
            throw new UserNotAuthenticatedException(Constants.USER_NOT_AUTHENTICATED);
        }
    }

    private void validateClientOrderStatus(Long idClient) {
        List<String> states = List.of(Constants.STATE_PENDING, Constants.STATE_IN_PREPARATION, Constants.STATE_READY);
        if (orderPersistencePort.existsByIdClientAndState(idClient, states.get(0)) ||
                orderPersistencePort.existsByIdClientAndState(idClient, states.get(1)) ||
                orderPersistencePort.existsByIdClientAndState(idClient, states.get(2))) {
            throw new ClientHasAnOrderException(Constants.CLIENT_HAS_AN_ORDER);
        }
    }

    public Restaurant getRestaurantById(Long restaurantId) {
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

    public void validateDishes(List<OrderDishRequestModel> orderDishes, Restaurant restaurant) {
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
