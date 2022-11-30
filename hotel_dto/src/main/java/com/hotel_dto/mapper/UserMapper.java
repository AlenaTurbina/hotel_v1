package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.Role;
import com.hotel_domain.model.entity.User;
import com.hotel_domain.model.entity.UserStatus;
import com.hotel_dto.dto.UserDto;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Named("rolesToRolesName")
    static List<String> rolesToRolesName(List<Role> roles) {
        return roles.stream().map(role -> role.getName()).collect(Collectors.toList());
    }

    @Named("userStatusToUserStatusName")
    static String userStatusToUserStatusName(UserStatus userStatus) {
        return userStatus.getName();
    }

    @Named("userStatusToUserStatusId")
    static UUID userStatusToUserStatusId(UserStatus userStatus) {
        return userStatus.getId();
    }

    //Mapping with all fields
    @Mappings({
            @Mapping(target = "rolesName", source = "roles", qualifiedByName = "rolesToRolesName"),
            @Mapping(target = "userStatusName", source = "userStatus", qualifiedByName = "userStatusToUserStatusName"),
            @Mapping(target = "userStatusId", source = "userStatus", qualifiedByName = "userStatusToUserStatusId"),
            @Mapping(target = "secretPassword", source = "password"),
    })
    @Named("userToUserDTO")
    UserDto toUserDTO(User user);

    //Mapping with selected fields for authentication
    @Mappings({
            @Mapping(target = "rolesName", source = "roles", qualifiedByName = "rolesToRolesName"),
            @Mapping(target = "userStatusId", source = "userStatus", qualifiedByName = "userStatusToUserStatusId"),
            @Mapping(target = "secretPassword", source = "password"),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "firstName", ignore = true),
            @Mapping(target = "lastName", ignore = true),
            @Mapping(target = "phoneNumber", ignore = true),
            @Mapping(target = "document", ignore = true),
            @Mapping(target = "userStatusName", ignore = true),
    })
    UserDto toUserDtoAuth(User user);

    @IterableMapping(qualifiedByName = "userToUserDTO")
    List<UserDto> toListUserDto(List<User> users);
}
