package com.example.foodcourtmicroservice.domain.model;

import com.example.foodcourtmicroservice.domain.usecase.Token;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private String name;
    private String surname;
    private String mail;
    private String phone;
    private String dniNumber;
    private LocalDate birthdate;

    public void setPin(String pin) {
        Token.setPin(pin);
    }
}
