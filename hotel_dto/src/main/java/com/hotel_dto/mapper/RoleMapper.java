package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.Role;
import com.hotel_dto.dto.RoleDTO;

import java.util.List;

public interface RoleMapper {
    RoleDTO toRoleDTO(Role role);

    List<RoleDTO> toListRoleDTO(List<Role> roles);

}
