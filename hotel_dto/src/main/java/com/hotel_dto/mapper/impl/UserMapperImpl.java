package com.hotel_dto.mapper.impl;

import com.hotel_domain.model.entity.Role;
import com.hotel_domain.model.entity.User;
import com.hotel_dto.dto.UserDTO;
import com.hotel_dto.mapper.UserMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toUserDTO(User user) {
        List<String> rolesString = new ArrayList<>();
        for (Role role : user.getRoles()) {
            rolesString.add(role.getName());
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setDocument(user.getDocument());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRolesString(rolesString);
        userDTO.setUserStatusString(user.getUserStatus().getName());
        return userDTO;
    }

    @Override
    public UserDTO toUserDTOAuth(User user) {
        List<String> rolesString = new ArrayList<>();
        for (Role role : user.getRoles()) {
            rolesString.add(role.getName());
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setRolesString(rolesString);
        userDTO.setUserStatusInteger(user.getUserStatus().getId());
        return userDTO;
    }


    @Override
    public List<UserDTO> toListUserDTO(List<User> users) {
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User user : users) {
            usersDTO.add(toUserDTO(user));
        }
        return usersDTO;
    }

}
