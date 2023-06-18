package com.example.foodcourtmicroservice.domain.model;


import lombok.AllArgsConstructor;
        import lombok.Getter;
        import lombok.NoArgsConstructor;
        import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SmsMessageModel {
    private String phone;
    private String message;
}