package com.hotel_server.service;

import com.hotel_domain.model.entity.User;
import com.hotel_dto.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUserByEmail(String email);

    User saveUser(UserDTO userDTO);

    User updateUser(UserDTO userDTO);
}
