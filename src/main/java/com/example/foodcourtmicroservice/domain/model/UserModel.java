package com.example.foodcourtmicroservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
