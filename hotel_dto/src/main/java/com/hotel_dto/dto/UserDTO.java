package com.hotel_dto.dto;

import lombok.Data;

import javax.persistence.Transient;
import java.util.List;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String document;
    private String secretPassword;

    private List<String> rolesString;
    private String userStatusString;
    private UUID userStatusInteger;

    @Transient
    private String confirmPassword;





}
