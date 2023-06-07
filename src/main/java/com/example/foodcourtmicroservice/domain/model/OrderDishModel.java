package com.example.foodcourtmicroservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDishModel {
    private Long id;
    private OrderModel order;
    private Dish dish;
    private String amount;
}
