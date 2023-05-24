package com.example.foodcourtmicroservice.domain.spi;

import com.example.foodcourtmicroservice.domain.model.UserModel;

public interface IUserFeignClientPort {


    Boolean existsUserById(Long userId);

    UserModel getUserById(Long userId);

    UserModel getUserByEmail(String mail);
}
