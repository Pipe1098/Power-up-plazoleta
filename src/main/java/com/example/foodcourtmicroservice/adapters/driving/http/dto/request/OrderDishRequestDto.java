package com.example.foodcourtmicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDishRequestDto {
    @NotNull(message = "El ID del plato no puede ser nulo")
    private Long idPlatos;
    @NotNull(message = "La cantidad no puede ser nula")
    private Long cantidad;
}
