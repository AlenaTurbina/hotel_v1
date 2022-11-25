package com.hotel_server.contollerRest;

import com.hotel_database.model.repository.UserRepository;
import com.hotel_dto.dto.UserDTO;
import com.hotel_dto.mapper.UserMapper;
import com.hotel_server.service.UserService;
import com.hotel_server.validator.UserClientUpdateValidator;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserRestController.class)
class UserRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private UserMapper userMapper;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserClientUpdateValidator userClientUpdateValidator;

    UserDTO userDTO = new UserDTO();
    String email;

    @BeforeEach
    public void setUp() {
        email = "test@test.com";
        userDTO.setId(UUID.randomUUID());
        userDTO.setFirstName("A");
        userDTO.setLastName("B");
        userDTO.setEmail(email);
    }

    @Test
    void testGetAllUsers() throws Exception {
        List<UserDTO> userDTOList = new ArrayList<>(List.of(userDTO));
        Mockito.when(userMapper.toListUserDTO(any())).thenReturn(userDTOList);
        mockMvc.perform(get("/api/admin/users")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].email", Matchers.equalTo(userDTO.getEmail())))
                .andExpect(content().json(asJsonString(userDTOList)))
                .andDo(print());
    }

    @Test
    void testGetUserOnEmail() throws Exception {
        Mockito.when(userMapper.toUserDTO(any())).thenReturn(userDTO);
        mockMvc.perform(post("/api/client/user")
                .content(asJsonString(email))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(userDTO)))
                .andDo(print());
    }

    @Test
    void testUpdateClientUser() throws Exception {
        Mockito.when(userMapper.toUserDTO(any())).thenReturn(userDTO);
        Mockito.when(userClientUpdateValidator.supports(any())).thenReturn(true);
        mockMvc.perform(put("/api/client/user/update")
                .content(asJsonString(userDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(userDTO)))
                .andDo(print());
    }

    @Test
    void testCheckAuthentication() throws Exception {
        Mockito.when(userMapper.toUserDTOAuth(any())).thenReturn(userDTO);
        mockMvc.perform(post("/api/auth")
                .content(asJsonString(email))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(userDTO)))
                .andDo(print());
    }
}