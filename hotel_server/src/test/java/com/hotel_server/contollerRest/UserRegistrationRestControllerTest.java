package com.hotel_server.contollerRest;

import com.hotel_dto.mapper.UserMapper;
import com.hotel_server.service.UserService;
import com.hotel_server.validator.UserValidator;
import com.hotel_dto.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.hotel_server.util.Utils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRegistrationRestController.class)
class UserRegistrationRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    UserService userService;
    @MockBean
    UserMapper userMapper;
    @MockBean
    UserValidator userValidator;

    @Test
    void testRegisterUserAccount() throws Exception {
        String email = "test@test.com";
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setFirstName("A");
        userDTO.setLastName("B");
        userDTO.setEmail(email);

        Mockito.when(userValidator.supports(any())).thenReturn(true);
        mockMvc.perform(post("/api/registration")
                        .content(asJsonString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}