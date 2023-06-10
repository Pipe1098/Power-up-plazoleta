package com.example.foodcourtmicroservice.configuration;

import com.example.foodcourtmicroservice.domain.exception.DishNoFoundException;
import com.example.foodcourtmicroservice.domain.exception.NoDataFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.Map;

import static com.example.foodcourtmicroservice.configuration.Constants.DISH_NOT_FOUND;
import static com.example.foodcourtmicroservice.configuration.Constants.NO_DATA_FOUND_MESSAGE;
import static com.example.foodcourtmicroservice.configuration.Constants.RESPONSE_ERROR_MESSAGE_KEY;

@ControllerAdvice
public class ControllerAdvisor {
 /*   @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(NoDataFoundException noDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, NO_DATA_FOUND_MESSAGE));
    }*/
    @ExceptionHandler(DishNoFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            DishNoFoundException userNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, DISH_NOT_FOUND));
    }
}
