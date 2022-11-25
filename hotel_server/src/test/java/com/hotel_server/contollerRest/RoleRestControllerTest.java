package com.hotel_server.contollerRest;

import com.hotel_dto.dto.RoleDTO;
import com.hotel_dto.mapper.RoleMapper;
import com.hotel_server.service.RoleService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.hotel_server.util.Utils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleRestController.class)
class RoleRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RoleService roleService;
    @MockBean
    private RoleMapper roleMapper;

    RoleDTO roleDTO = new RoleDTO();
    UUID uuid = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        roleDTO.setId(uuid);
    }

    @Test
    void testGetAllRoles() throws Exception {
        List<RoleDTO> roleDTOList = new ArrayList<>(List.of(roleDTO));
        Mockito.when(roleMapper.toListRoleDTO(any())).thenReturn(roleDTOList);
        mockMvc.perform(get("/api/admin/roles")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(roleDTO.getId().toString())))
                .andExpect(content().json(asJsonString((roleDTOList))))
                .andDo(print());
    }
}