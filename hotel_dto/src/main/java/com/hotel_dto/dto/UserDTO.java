package com.hotel_dto.dto;

import lombok.Data;

import javax.persistence.Transient;
import java.util.List;

@Data
public class UserDTO {
    private Integer id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String document;
    private String secretPassword;

    private List<String> rolesString;
    private String userStatusString;
    private Integer userStatusInteger;

    @Transient
    private String confirmPassword;

}
