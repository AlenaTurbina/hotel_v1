package com.hotel_server.contollerRest;

import com.hotel_dto.dto.RoleDTO;
import com.hotel_dto.mapper.RoleMapper;
import com.hotel_server.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name="Role", description = "Management of Role - getting list")
public class RoleRestController {
    private RoleService roleService;
    private RoleMapper roleMapper;

    @Operation(summary = "Getting list of Roles")
    @GetMapping("/admin/roles")
    List<RoleDTO> getAllRoles() {
        return roleMapper.toListRoleDTO(roleService.getAllRoles());
    }

}
