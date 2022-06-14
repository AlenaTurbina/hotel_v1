package com.hotel_server.service.impl;

import com.hotel_database.model.repository.RoleRepository;
import com.hotel_domain.model.entity.Role;
import com.hotel_server.exceptionHandler.exception.ServerEntityNotFoundException;
import com.hotel_server.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAllRoles() {
        log.info("Role getAll");
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleById(Integer id) {
        log.info("Get role by id " + id);
        return roleRepository.findById(id).orElseThrow(() -> new ServerEntityNotFoundException(id));
    }
}
