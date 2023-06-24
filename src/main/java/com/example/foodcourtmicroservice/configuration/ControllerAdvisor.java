package com.example.foodcourtmicroservice.configuration;

import com.example.foodcourtmicroservice.domain.exception.CategoryNotFoundException;
import com.example.foodcourtmicroservice.domain.exception.ClientAuthMustBeEqualsClientOrderException;
import com.example.foodcourtmicroservice.domain.exception.ClientHasAnOrderException;
import com.example.foodcourtmicroservice.domain.exception.DishIdRestaurantIsNotEqualsOrderException;
import com.example.foodcourtmicroservice.domain.exception.DishIsInactiveException;
import com.example.foodcourtmicroservice.domain.exception.DishNoFoundException;
import com.example.foodcourtmicroservice.domain.exception.DishNotExistException;
import com.example.foodcourtmicroservice.domain.exception.InvalidOrderStateException;
import com.example.foodcourtmicroservice.domain.exception.InvalidPinException;
import com.example.foodcourtmicroservice.domain.exception.NoCancelOrdersPreparationOrReadyException;
import com.example.foodcourtmicroservice.domain.exception.NoDataFoundException;
import com.example.foodcourtmicroservice.domain.exception.OnlyCancelOrderStatusPendingException;
import com.example.foodcourtmicroservice.domain.exception.OrderNotExistException;
import com.example.foodcourtmicroservice.domain.exception.OrderRestaurantMustBeEqualsEmployeeRestaurantException;
import com.example.foodcourtmicroservice.domain.exception.OwnerAnotherRestaurantException;
import com.example.foodcourtmicroservice.domain.exception.OwnerMustOnlyOwnARestaurantException;
import com.example.foodcourtmicroservice.domain.exception.RestaurantNoFoundException;
import com.example.foodcourtmicroservice.domain.exception.UserMustBeOwnerException;
import com.example.foodcourtmicroservice.domain.exception.UserNoAutorizedException;
import com.example.foodcourtmicroservice.domain.exception.UserNotAuthenticatedException;
import com.example.foodcourtmicroservice.domain.exception.UserNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

import static com.example.foodcourtmicroservice.configuration.Constants.CATEGORY_NOT_FOUND;
import static com.example.foodcourtmicroservice.configuration.Constants.CLIENT_HAS_AN_ORDER;
import static com.example.foodcourtmicroservice.configuration.Constants.DIFFERENT_OWNER;
import static com.example.foodcourtmicroservice.configuration.Constants.DISH_IDRESTAURANT_DIFERENT;
import static com.example.foodcourtmicroservice.configuration.Constants.DISH_NOT_ACTIVE;
import static com.example.foodcourtmicroservice.configuration.Constants.DISH_NOT_EXIST;
import static com.example.foodcourtmicroservice.configuration.Constants.DISH_NOT_FOUND;
import static com.example.foodcourtmicroservice.configuration.Constants.IDEMPLOYEE_IDORDER_DIFERENT;
import static com.example.foodcourtmicroservice.configuration.Constants.NO_DATA_FOUND_MESSAGE;
import static com.example.foodcourtmicroservice.configuration.Constants.ORDER_NOT_EXIST;
import static com.example.foodcourtmicroservice.configuration.Constants.RESPONSE_ERROR_MESSAGE_KEY;
import static com.example.foodcourtmicroservice.configuration.Constants.RESTAURANT_NOT_FOUND;
import static com.example.foodcourtmicroservice.configuration.Constants.UNAUTHORIZED_USER;
import static com.example.foodcourtmicroservice.configuration.Constants.USER_PERMISSION_DENIED;

@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(NoDataFoundException noDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, NO_DATA_FOUND_MESSAGE));
    }
    @ExceptionHandler(DishNoFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            DishNoFoundException userNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, DISH_NOT_FOUND));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleCategoryNoFoundException(CategoryNotFoundException categoryNotFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, CATEGORY_NOT_FOUND));
    }
    @ExceptionHandler(ClientAuthMustBeEqualsClientOrderException.class)
    public ResponseEntity<Map<String, String>> handleClientAuthMustBeEqualsClientOrderException(
            ClientAuthMustBeEqualsClientOrderException ClientAuthMustBeEqualsClientOrderException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY,"The authenticated client has to be the same client of the order" ));
    }

    @ExceptionHandler(ClientHasAnOrderException.class)
    public ResponseEntity<Map<String, String>> handleClientHasAnOrderException(ClientHasAnOrderException ClientHasAnOrderException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, CLIENT_HAS_AN_ORDER));
    }


    @ExceptionHandler(DishIdRestaurantIsNotEqualsOrderException.class)
    public ResponseEntity<Map<String, String>> handleDishIdRestaurantIsNotEqualsOrderException(DishIdRestaurantIsNotEqualsOrderException dishIdRestaurantIsNotEqualsOrderException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, DISH_IDRESTAURANT_DIFERENT));
    }
    @ExceptionHandler(DishIsInactiveException.class)
    public ResponseEntity<Map<String, String>> handleDishIsInactiveException(DishIsInactiveException dishIsInactiveException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, DISH_NOT_ACTIVE));
    }

    @ExceptionHandler(DishNotExistException.class)
    public ResponseEntity<Map<String, String>> DishNotExistException(DishNotExistException dishNotExistException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, DISH_NOT_EXIST));
    }



    @ExceptionHandler(InvalidOrderStateException.class)
    public ResponseEntity<Map<String, String>> handleInvalidOrderStateException(InvalidOrderStateException invalidOrderStateException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, "Only orders in 'READY' state can be delivered."));
    }
    @ExceptionHandler(InvalidPinException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPinException(
            InvalidPinException DishIsInactiveException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, "Invalid pin entered."));
    }

    @ExceptionHandler(NoCancelOrdersPreparationOrReadyException.class)
    public ResponseEntity<Map<String, String>> handleNoCancelOrdersPreparationOrReadyException(NoCancelOrdersPreparationOrReadyException noCancelOrdersPreparationOrReadyException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, "Orders with status IN_PREPARATION or READY cannot be canceled"));
    }



    @ExceptionHandler(OnlyCancelOrderStatusPendingException.class)
    public ResponseEntity<Map<String, String>> handleOnlyCancelOrderStatusPendingException(OnlyCancelOrderStatusPendingException onlyCancelOrderStatusPendingException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, "Only Orders with status PENDING  can be canceled"));
    }
    @ExceptionHandler(OrderNotExistException.class)
    public ResponseEntity<Map<String, String>> handleOrderNotExistException(
            OrderNotExistException orderNotExistException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ORDER_NOT_EXIST));
    }

    @ExceptionHandler(OrderRestaurantMustBeEqualsEmployeeRestaurantException.class)
    public ResponseEntity<Map<String, String>> handleOrderRestaurantMustBeEqualsEmployeeRestaurantException(OrderRestaurantMustBeEqualsEmployeeRestaurantException orderRestaurantMustBeEqualsEmployeeRestaurantException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, IDEMPLOYEE_IDORDER_DIFERENT));
    }


    @ExceptionHandler(OwnerAnotherRestaurantException.class)
    public ResponseEntity<Map<String, String>> handleOwnerAnotherRestaurantException(OwnerAnotherRestaurantException ownerAnotherRestaurantException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, DIFFERENT_OWNER));
    }
    @ExceptionHandler(OwnerMustOnlyOwnARestaurantException.class)
    public ResponseEntity<Map<String, String>> handleOwnerMustOnlyOwnARestaurantException(
            OwnerMustOnlyOwnARestaurantException ownerMustOnlyOwnARestaurantException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, "This owner have other restaurant"));
    }

    @ExceptionHandler(RestaurantNoFoundException.class)
    public ResponseEntity<Map<String, String>> handleRestaurantNoFoundException(RestaurantNoFoundException restaurantNoFoundException){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, RESTAURANT_NOT_FOUND));
    }



    @ExceptionHandler(UserMustBeOwnerException.class)
    public ResponseEntity<Map<String, String>> handleUserMustBeOwnerException(UserMustBeOwnerException userMustBeOwnerException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, USER_PERMISSION_DENIED));
    }
    @ExceptionHandler(UserNoAutorizedException.class)
    public ResponseEntity<Map<String, String>> handleUserNoAutorizedException(
            UserNoAutorizedException userNoAutorizedException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, UNAUTHORIZED_USER));
    }

    @ExceptionHandler(UserNotAuthenticatedException.class)
    public ResponseEntity<Map<String, String>> handleUserNotAuthenticatedException(UserNotAuthenticatedException userNotAuthenticatedException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, UNAUTHORIZED_USER));
    }

    @ExceptionHandler(UserNotExistException.class)
    public ResponseEntity<Map<String, String>> handleUserNotExistException(UserNotExistException userNotExistException){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, "The user doesn't exist"));
    }

}
