package com.example.foodcourtmicroservice.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDishResponseModel {
    private Long id;
    private String name;
    private String price;
    private String description;
    private String urlImage;
    private Category categoryId;

    private String amount;
}
