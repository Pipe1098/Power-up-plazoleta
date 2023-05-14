package com.example.foodcourtmicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class RestaurantRequestDto {
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9]+$", message = "El nombre puede ser alfanúmerico pero no solo númerico")
    @NotBlank
    private String name;
    @NotBlank(message = "La dirección es requerida")
    private String direction;
    @Pattern(regexp = "^[0-9+]{1,13}$", message = "El telefono debe contener máximo 13 caracteres y puede contener el símbolo '+' al inicio")
    @NotBlank(message = "El telefono es requerido")
    private String phone;
    @NotBlank(message = "el urlLogo es requerido")
    private String urlLogotype;
    @Pattern(regexp = "^[0-9]+$", message = "El nit debe ser númerico")
    @NotBlank(message = "El nit es requerido")
    private String nit;
    @NotNull(message = "El id_propietario no puede ser nulo")
    @Min(value = 1, message = "El id_propietario debe ser mayor a cero")
    @NotBlank(message = "El id_propietarios es requerido")
    private String idOwner;

}

