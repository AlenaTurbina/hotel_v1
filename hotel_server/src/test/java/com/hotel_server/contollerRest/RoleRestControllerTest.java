package com.hotel_server.contollerRest;

import com.hotel_dto.mapper.RoleMapper;
import com.hotel_server.service.RoleService;
import com.hotel_dto.dto.RoleDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

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


    @Test
    void testGetAllRoles() throws Exception {
        Integer id = 1;
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(id);

        List<RoleDTO> roleDTOList = new ArrayList<>(List.of(roleDTO));
        Mockito.when(roleMapper.toListRoleDTO(any())).thenReturn(roleDTOList);

        mockMvc.perform(get("/api/admin/roles")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(roleDTO.getId())))
                .andExpect(content().json(asJsonString((roleDTOList))))
                .andDo(print());
    }
}