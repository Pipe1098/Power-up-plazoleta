package com.example.foodcourtmicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotBlank;
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
public class DishRequestDto {
    @NotBlank
    private String name;
    @NotNull
    private Long idCategory;
    @NotBlank
    private String description;
    @NotBlank
    @Positive
    @Pattern(regexp = "^[0-9]+$", message = "Price must be numeric only")
    private String price;
    @NotNull
    private Long idRestaurant;
    @NotBlank
    private String urlImage;

}
