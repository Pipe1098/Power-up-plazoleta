package com.example.foodcourtmicroservice.adapters.driving.http.controller;


import com.example.foodcourtmicroservice.adapters.driving.http.dto.request.OrderRequestDto;
import com.example.foodcourtmicroservice.adapters.driving.http.dto.response.OrderResponseDto;
import com.example.foodcourtmicroservice.adapters.driving.http.handlers.IOrderHandler;
import com.example.foodcourtmicroservice.configuration.Constants;
import com.example.foodcourtmicroservice.domain.model.EmployeeRanking;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderRestController {

    private final IOrderHandler orderHandler;

    @Operation(summary = "Add a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Order already exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "403", description = "No authorized", content = @Content)
    })
    @PostMapping("/")
   // @PreAuthorize("hasAuthority('CLIENT_ROLE')")
    public ResponseEntity<OrderResponseDto> AddAnOrder(@Validated @RequestBody OrderRequestDto orderRequest) {
        orderHandler.saveOrder(orderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Get Orders By State")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "302", description = "Orders found", content = @Content),
            @ApiResponse(responseCode = "404", description = "Orders don't exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "bad request", content = @Content),
            @ApiResponse(responseCode = "403", description = "No authorized", content = @Content)
    })
    @GetMapping("/getOrdersByState/page/{page}/size/{size}/state/{state}")
    public ResponseEntity<List<OrderResponseDto>> getAllOrderByState(@PathVariable("page") Integer page, @PathVariable("size") Integer size, @PathVariable(value = "state") String state) {
        if (size <= 0L || page < 0L || state.isBlank() || state.isEmpty())
        {return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();}
        return ResponseEntity.ok(orderHandler.getAllOrdersWithPagination(page, size, state));
    }

    @Operation(summary = "Take order and update state")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order taken", content = @Content),
            @ApiResponse(responseCode = "409", description = "Order doesn't exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "403", description = "No authorized", content = @Content)
    })
    @PutMapping("/takeOrderAndUpdateStatus/{idOrder}/status/{state}")
    public ResponseEntity<Void> takeOrderAndUpdateStatus(@PathVariable("idOrder") Long idOrder, @PathVariable("state") String state) {
        if (idOrder <= 0L || state.isBlank() || state.isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        orderHandler.takeOrderAndUpdateState(idOrder, state);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Notify that Order is ready")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order ready", content = @Content),
            @ApiResponse(responseCode = "409", description = "Order doesn't exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "403", description = "No authorized", content = @Content)
    })
    @PutMapping("/notifyOrderReady/{idOrder}")
    public ResponseEntity<Map<String,String>> notifyOrderReady(@PathVariable("idOrder") Long idOrder) {
        if (idOrder <= 0L) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        orderHandler.notifyOrderReady(idOrder);
        return ResponseEntity.status(HttpStatus.OK).body(
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY, Constants.ORDER_READY));
    }

    @Operation(summary = "Deliver order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order deliver", content = @Content),
            @ApiResponse(responseCode = "409", description = "Order doesn't exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "403", description = "No authorized", content = @Content)
    })
    @PutMapping("/deliverOrder/{idOrder}/pin/{pin}")
    public ResponseEntity<Map<String,String>> orderDeliver(@PathVariable("idOrder") Long idOrder, @PathVariable("pin") String pin) {
        if (idOrder <= 0L) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        orderHandler.deliverOrder(idOrder, pin);
        return ResponseEntity.status(HttpStatus.OK).body(
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY,Constants.ORDER_DELIVERED));
    }
    @Operation(summary = "Cancel Order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order cancel", content = @Content),
            @ApiResponse(responseCode = "409", description = "Order doesn't exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "403", description = "No authorized", content = @Content)
    })
    @PutMapping("/cancelOrder/{idOrder}")
    public ResponseEntity<Map<String,String>> cancelOrder(@PathVariable("idOrder") Long idOrder){
        if(idOrder <= 0L)  return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        orderHandler.cancelOrder(idOrder);
        return ResponseEntity.status(HttpStatus.OK).body(
                Collections.singletonMap(Constants.RESPONSE_MESSAGE_KEY,Constants.ORDER_CANCELED));
    }

    @Operation(summary = "Get logs by order Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logs list", content = @Content),
            @ApiResponse(responseCode = "409", description = "Logs doesn't exists", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "403", description = "No authorized", content = @Content)
    })
    @GetMapping("/logs/{idOrder}")
    public String getLogs(@PathVariable("idOrder")Long idOrder){
        return orderHandler.getLogs(idOrder);
    }


    @Operation(summary = "Get efficiency by restaurant Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ranking list", content = @Content),
            @ApiResponse(responseCode = "409", description = "list doesn't exists yet", content = @Content),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content),
            @ApiResponse(responseCode = "403", description = "No authorized", content = @Content)
    })
    @GetMapping("/efficiency/{idRestaurant}")
    public List<EmployeeRanking> getEfficiency(@PathVariable("idRestaurant") Long idRestaurant){
        return orderHandler.getEfficiency(idRestaurant);
    }
}
