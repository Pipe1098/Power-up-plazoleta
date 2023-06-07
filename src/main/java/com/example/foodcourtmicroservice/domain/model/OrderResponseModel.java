package com.example.foodcourtmicroservice.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseModel {
    private Long id;
    private Long idClient;
    private Long idEmployee;
    private Date date;
    private List<OrderDishResponseModel> orderDishes;
}
