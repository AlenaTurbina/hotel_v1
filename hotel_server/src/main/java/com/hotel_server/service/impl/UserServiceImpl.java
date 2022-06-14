package com.hotel_server.service.impl;

import com.hotel_server.message.Messages;
import com.hotel_database.model.repository.UserRepository;
import com.hotel_domain.model.entity.Role;
import com.hotel_domain.model.entity.User;
import com.hotel_dto.dto.UserDTO;
import com.hotel_server.service.RoleService;
import com.hotel_server.service.UserService;
import com.hotel_server.service.UserStatusService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleService roleService;
    private UserStatusService userStatusService;
    private PasswordEncoder passwordEncoder;

    private static final Integer ID_DEFAULT_ROLE_CLIENT = Messages.getIntegerMessage("server.booking.idDefaultRoleClient");
    private static final Integer ID_DEFAULT_USER_STATUS_ACTIVE = Messages.getIntegerMessage("server.booking.idDefaultUserStatusActive");


    @Override
    public List<User> getAllUsers() {
        log.info("User getAll");
        return userRepository.findAll();
    }

    @Override
    public User getUserByEmail(String email){
        User user =  userRepository.findByEmail(email);
        if (user != null) {
            log.info("User getByName is not null (name): " + email);
            return user;
        } else {
            log.info("Class apartment getByName is null (name): " + email);
            return null;
        }
    }

    @Override
    @Transactional
    public User saveUser(UserDTO userDTO) {
        List<Role> roles = new ArrayList<>(List.of(roleService.getRoleById(ID_DEFAULT_ROLE_CLIENT)));
        var user = User.builder()
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .phoneNumber(userDTO.getPhoneNumber())
                .document(userDTO.getDocument())
                .roles(roles)
                .userStatus(userStatusService.getUserStatusById(ID_DEFAULT_USER_STATUS_ACTIVE))
                .build();
        var userSave = userRepository.saveAndFlush(user);
        log.info("User was saved (user id): " + userSave.getId());
        return userSave;
    }

    @Override
    public User updateUser(UserDTO userDTO) {
        User userUpdate = userRepository.getById(userDTO.getId());
        userUpdate.setFirstName(userDTO.getFirstName());
        userUpdate.setLastName(userDTO.getLastName());
        userUpdate.setDocument(userDTO.getDocument());
        userUpdate.setPhoneNumber(userDTO.getPhoneNumber());
        userUpdate.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userUpdate.setEmail(userDTO.getEmail());
        log.info("User update with set-fields (user id): " + userUpdate.getId());
        return userRepository.saveAndFlush(userUpdate);
    }
}
