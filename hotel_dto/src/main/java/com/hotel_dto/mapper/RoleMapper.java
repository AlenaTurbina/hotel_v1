package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.Role;
import com.hotel_dto.dto.RoleDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleDto toRoleDTO(Role role);

    List<RoleDto> toListRoleDTO(List<Role> roles);
}
