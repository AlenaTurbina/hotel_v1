package com.hotel_dto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private UUID id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String document;
    private String secretPassword;
    private List<String> rolesName;
    private String userStatusName;
    private UUID userStatusId;

    @Transient
    private String confirmPassword;
}
