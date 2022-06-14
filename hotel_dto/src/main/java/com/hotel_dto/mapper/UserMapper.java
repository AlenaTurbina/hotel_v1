package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.User;
import com.hotel_dto.dto.UserDTO;

import java.util.List;

public interface UserMapper {
    UserDTO toUserDTO(User user);

    UserDTO toUserDTOAuth(User user);

    List<UserDTO> toListUserDTO(List<User> users);

}
