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
    @Pattern(regexp = "^(?=.*[a-zA-Z])[a-zA-Z0-9]+$", message = "The name can be alphanumeric but not purely numeric")
    @NotBlank
    private String name;
    @NotBlank(message = "The address is required")
    private String address;
    @Pattern(regexp = "^[0-9+]{1,13}$", message = "The phone must contain a maximum of 13 characters and can include the symbol '+' at the beginning")
    @NotBlank(message = "The phone is required")
    private String phone;
    @NotBlank(message = "The urlLogotype is required")
    private String urlLogotype;
    @Pattern(regexp = "^[0-9]+$", message = "The NIT must be numeric")
    @NotBlank(message = "The NIT is required")
    private String nit;
    @NotNull(message = "The idOwner cannot be null")
    @Min(value = 1, message = "The idOwner must be greater than zero")
    @NotBlank(message = "The idOwner is required")
    private String idOwner;
}

