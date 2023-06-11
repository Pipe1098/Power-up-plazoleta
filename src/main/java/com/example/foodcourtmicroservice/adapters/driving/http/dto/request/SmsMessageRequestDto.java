package com.example.foodcourtmicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmsMessageRequestDto {
    @NotBlank(message = "the phone is required")
    @Pattern(regexp = "^\\+57\\d{10}$", message = "phone must start with +57 and have 10 numbers")
    private String phone;

    @NotBlank(message = "Message is required")
    private String message;
}
