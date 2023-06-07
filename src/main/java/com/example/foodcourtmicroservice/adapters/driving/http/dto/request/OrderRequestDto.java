package com.example.foodcourtmicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {

    @NotNull(message = "Dish list must not be null")
    @Size(min = 1, message = "There must be at least one dish on the list")
    private List<OrderDishRequestDto> dishes;
    @NotNull(message = "Restaurant id cannot be null")
    private Long resturantId;
}
