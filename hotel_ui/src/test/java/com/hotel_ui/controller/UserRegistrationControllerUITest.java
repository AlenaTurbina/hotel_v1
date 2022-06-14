package com.hotel_ui.controller;

import com.hotel_dto.dto.UserDTO;
import com.hotel_ui.configuration.TestConfigurationUserDetails;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = TestConfigurationUserDetails.class)
@AutoConfigureMockMvc
class UserRegistrationControllerUITest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private RestTemplate restTemplate;


    @Test
    void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/registration")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("account/registration"))
                .andDo(print());
    }

    @Test
    void testRegisterUserAccountWithErrors() throws Exception {
        Map<String, String> mapErrors = new HashMap();
        mapErrors.put("A", "B");

        Mockito.when(restTemplate.postForEntity(anyString(), any(UserDTO.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(mapErrors, HttpStatus.OK));
        mockMvc.perform(post("/registration")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(view().name("account/registration"))
                .andDo(print());
    }

    @Test
    void testRegisterUserAccountWithoutErrors() throws Exception {
        Mockito.when(restTemplate.postForEntity(anyString(), any(UserDTO.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(HttpStatus.CREATED));
        mockMvc.perform(post("/registration")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/registration?success"))
                .andDo(print());
    }
}