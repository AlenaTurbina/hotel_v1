package com.hotel_dto.mapper.impl;

import com.hotel_domain.model.entity.Role;
import com.hotel_dto.dto.RoleDTO;
import com.hotel_dto.mapper.RoleMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public RoleDTO toRoleDTO(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }

    @Override
    public List<RoleDTO> toListRoleDTO(List<Role> roles) {
        List<RoleDTO> roleDTOList = new ArrayList<>();
        for (Role role : roles) {
            roleDTOList.add(toRoleDTO(role));
        }
        return roleDTOList;
    }
}
