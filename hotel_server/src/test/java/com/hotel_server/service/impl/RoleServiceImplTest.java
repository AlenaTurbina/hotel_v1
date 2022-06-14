package com.hotel_server.service.impl;

import com.hotel_domain.model.entity.Role;
import com.hotel_database.model.repository.RoleRepository;
import com.hotel_domain.model.entity.RoomType;
import com.hotel_server.exceptionHandler.exception.ServerEntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;

    @BeforeEach
    public void setup() {
       role = Role.builder()
                .id(1)
                .name("R1")
                .build();
    }

    @DisplayName("JUnit test for getAllRoles method")
    @Test
    void test_WhenGetAllRoles_ThenReturnRoleList() {
        Role role1 = Role.builder()
                .id(2)
                .name("R2")
                .build();

        given(roleRepository.findAll()).willReturn(List.of(role, role1));
        List<Role> roleList = roleService.getAllRoles();

        assertThat(roleList).isNotEmpty();
        assertThat(roleList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getAllRoles method (empty list)")
    @Test
    void test_WhenGetAllRoles_ThenReturnEmptyRoleList() {
        Role role1 = Role.builder()
                .id(2)
                .name("R2")
                .build();

        given(roleRepository.findAll()).willReturn(Collections.emptyList());
        List<Role> roleList = roleService.getAllRoles();

        assertThat(roleList).isEmpty();
        assertThat(roleList.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getRoleById method")
    @Test
    void test_GivenRoleId_WhenGetRoleById_thenReturnRole() {
        given(roleRepository.findById(role.getId()))
                .willReturn(Optional.of(role));
        Role roleExpected = roleService.getRoleById(role.getId());

        assertThat(roleExpected).isNotNull();
    }

    @DisplayName("JUnit test for getRoleById method (throw exception)")
    @Test
    void test_GivenRoleId_WhenGetRoleById_ThenThrowException() {
        given(roleRepository.findById(role.getId()))
                .willReturn(Optional.empty());

        Assertions.assertThrows(ServerEntityNotFoundException.class,
                () -> roleService.getRoleById(role.getId()));
    }

}