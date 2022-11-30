package com.hotel_ui.controller;

import com.hotel_dto.dto.ClassApartmentDto;
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
class ClassApartmentControllerUITest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private ClassApartmentDto classApartmentDto;

    @Test
    @WithUserDetails("admin@test.com")
    void testClassApartments() throws Exception {
        List<ClassApartmentDto> classApartmentDTOList = new ArrayList<>(List.of(classApartmentDto));
        Mockito.when(restTemplate.getForObject(any(), any())).thenReturn(classApartmentDTOList);

        mockMvc.perform(get("/admin/classApartments")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/classApartments"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testCreateClassApartmentForm() throws Exception {
        mockMvc.perform(get("/admin/classApartments/create")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/createClassApartments"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testCreateClassApartmentsWithErrors() throws Exception {
        Map<String, String> mapErrors = new HashMap();
        mapErrors.put("A", "B");

        Mockito.when(restTemplate.postForEntity(anyString(), any(ClassApartmentDto.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(mapErrors, HttpStatus.OK));
        mockMvc.perform(post("/admin/classApartments/create")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/createClassApartments"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testCreateClassApartmentsWithoutErrors() throws Exception {
        Mockito.when(restTemplate.postForEntity(anyString(), any(ClassApartmentDto.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(HttpStatus.CREATED));
        mockMvc.perform(post("/admin/classApartments/create")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/classApartments"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testUpdateClassApartmentForm() throws Exception {
        Mockito.when(restTemplate.getForObject(any(), eq(ClassApartmentDto.class))).thenReturn(classApartmentDto);
        mockMvc.perform(get("/admin/classApartments/update/{id}", "1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/updateClassApartments"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testUpdateClassApartmentsWithErrors() throws Exception {
        Map<String, String> mapErrors = new HashMap();
        mapErrors.put("A", "B");

        Mockito.when(restTemplate.exchange(anyString(), any(), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(mapErrors, HttpStatus.OK));
        mockMvc.perform(post("/admin/classApartments/update")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/updateClassApartments"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testUpdateClassApartmentsWithoutErrors() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), any(), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(HttpStatus.CREATED));

        mockMvc.perform(post("/admin/classApartments/update")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/classApartments"))
                .andDo(print());
    }
}