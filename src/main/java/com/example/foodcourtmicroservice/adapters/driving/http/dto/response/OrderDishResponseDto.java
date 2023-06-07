package com.example.foodcourtmicroservice.adapters.driving.http.dto.response;

import com.example.foodcourtmicroservice.domain.model.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDishResponseDto {
    private Long id;
    private String name;
    private String price;
    private String description;
    private String urlImage;
    private Category categoryId;
    private String amount;

}
