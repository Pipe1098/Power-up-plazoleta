package com.example.foodcourtmicroservice.adapters.driven.jpa.mysql.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Dishes")
@Getter
@Setter
public class DishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    private CategoryEntity idCategory;
    private String description;
    private String price;
    @OneToOne
    private RestaurantEntity idRestaurant;
    private String urlImage;
    private Boolean active;
}
