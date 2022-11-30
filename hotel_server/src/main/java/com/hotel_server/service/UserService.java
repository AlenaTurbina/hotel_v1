package com.hotel_server.service;

import com.hotel_domain.model.entity.User;
import com.hotel_dto.dto.UserDto;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserByEmail(String email);

    User saveUser(UserDto userDTO);

    User updateUser(UserDto userDTO);
}
