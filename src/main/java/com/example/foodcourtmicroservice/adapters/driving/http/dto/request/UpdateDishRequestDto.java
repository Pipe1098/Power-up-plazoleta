package com.example.foodcourtmicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateDishRequestDto {

    @NotNull
    private Long id;
    @Positive
    @Pattern(regexp = "^[0-9]+$", message = "\n" + "The price must only contain numbers")
    private String price;
    private String description;
}
