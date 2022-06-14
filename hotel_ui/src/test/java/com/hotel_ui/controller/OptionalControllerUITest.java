package com.hotel_ui.controller;

import com.hotel_dto.dto.OptionalDTO;
import com.hotel_ui.configuration.TestConfigurationUserDetails;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = TestConfigurationUserDetails.class)
@AutoConfigureMockMvc
class OptionalControllerUITest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private OptionalDTO optionalDTO;


    @Test
    @WithUserDetails("admin@test.com")
    void testGetAllOptionals() throws Exception {
        List<OptionalDTO> optionalDTOList = new ArrayList<>(List.of(optionalDTO));
        Mockito.when(restTemplate.getForObject(any(), any())).thenReturn(optionalDTOList);

        mockMvc.perform(get("/admin/optionals")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/optionals"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testCreateOptionalForm() throws Exception {
        mockMvc.perform(get("/admin/optionals/create")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/createOptionals"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testCreateOptionalWithErrors() throws Exception {
        Map<String, String> mapErrors = new HashMap();
        mapErrors.put("A", "B");

        Mockito.when(restTemplate.postForEntity(anyString(), any(OptionalDTO.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(mapErrors, HttpStatus.OK));
        mockMvc.perform(post("/admin/optionals/create")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/createOptionals"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testCreateOptionalWithoutErrors() throws Exception {
        Mockito.when(restTemplate.postForEntity(anyString(), any(OptionalDTO.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(HttpStatus.CREATED));

        mockMvc.perform(post("/admin/optionals/create")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/optionals"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testUpdateOptionalForm() throws Exception {
        Mockito.when(restTemplate.getForObject(any(), eq(OptionalDTO.class))).thenReturn(optionalDTO);

        mockMvc.perform(get("/admin/optionals/update/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/updateOptionals"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testUpdateOptionalsWithErrors() throws Exception {
        Map<String, String> mapErrors = new HashMap();
        mapErrors.put("A", "B");

        Mockito.when(restTemplate.exchange(anyString(), any(), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(mapErrors, HttpStatus.OK));
        mockMvc.perform(post("/admin/optionals/update")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/updateOptionals"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testUpdateOptionalsWithoutErrors() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), any(), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(HttpStatus.CREATED));

        mockMvc.perform(post("/admin/optionals/update")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/optionals"))
                .andDo(print());
    }
}