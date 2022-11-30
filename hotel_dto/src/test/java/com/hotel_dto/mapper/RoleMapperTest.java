package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.Role;
import com.hotel_dto.dto.RoleDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ComponentScan("com.hotel_domain")
public class RoleMapperTest {
    private RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);
    private Role role;
    public static final String id = "788c07d7-4275-4101-8fc3-1d77986f2129";

    @BeforeEach
    void setUp() {
        role = new Role(UUID.fromString(id), "A");
    }

    @Test
    public void givenToRoleDto_whenMapper_ThenCorrect() {
        RoleDto roleDto = roleMapper.toRoleDTO(role);

        assertEquals(role.getId(), roleDto.getId());
        assertEquals(role.getName(), roleDto.getName());
    }

    @Test
    public void givenToListRoleDto_whenMapper_ThenCorrect() {
        RoleDto roleDto = new RoleDto(
                role.getId(),
                role.getName()
        );

        assertEquals(List.of(roleDto), roleMapper.toListRoleDTO(List.of(role)));
    }
}
