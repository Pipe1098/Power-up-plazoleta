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
    private Long id;
    private String name;
    private String surname;
    private String dni;
    private String phone;
    private String email;
    private String password;
    private RoleModel role;


    public void setPin(String pin) {
        Token.setPin(pin);
    }
}
