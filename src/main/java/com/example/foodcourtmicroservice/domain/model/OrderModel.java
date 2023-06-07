package com.example.foodcourtmicroservice.domain.model;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {
    private Long id;
    private Long idClient;
    private LocalDate date;
    private String state;
    private Long idEmployee;
    private Restaurant restaurant;
}
