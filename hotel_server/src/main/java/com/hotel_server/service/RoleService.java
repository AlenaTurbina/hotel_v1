package com.hotel_server.service;

import com.hotel_domain.model.entity.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    Role getRoleById(Integer id);
}
