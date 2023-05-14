package com.example.foodcourtmicroservice.domain.spi;

import com.example.foodcourtmicroservice.domain.model.UserModel;

public interface IUserFeignClientPort {


    Boolean existsUserById(Long usuarioId);

    UserModel getUserById(Long usuarioId);

    UserModel getUserByEmail(String correo);
}
