package com.hotel_server.service;

import com.hotel_domain.model.entity.Role;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    List<Role> getAllRoles();

    Role getRoleById(UUID id);
}
