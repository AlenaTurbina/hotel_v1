package com.hotel_ui.controller;

import com.hotel_dto.dto.RoomTypeDTO;
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
class RoomTypeControllerUITest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private RoomTypeDTO roomTypeDTO;

    @Test
    @WithUserDetails("admin@test.com")
    void testRoomTypes() throws Exception {
        List<RoomTypeDTO> roomTypeDTOList = new ArrayList<>(List.of(roomTypeDTO));
        Mockito.when(restTemplate.getForObject(any(), any())).thenReturn(roomTypeDTOList);

        mockMvc.perform(get("/admin/roomTypes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/roomTypes"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testCreateRoomTypeForm() throws Exception {
        mockMvc.perform(get("/admin/roomTypes/create")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/createRoomTypes"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testCreateRoomTypeWithErrors() throws Exception {
        Map<String, String> mapErrors = new HashMap();
        mapErrors.put("A", "B");

        Mockito.when(restTemplate.postForEntity(anyString(), any(RoomTypeDTO.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(mapErrors, HttpStatus.OK));
        mockMvc.perform(post("/admin/roomTypes/create")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/createRoomTypes"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testCreateRoomTypeWithoutErrors() throws Exception {
        Mockito.when(restTemplate.postForEntity(anyString(), any(RoomTypeDTO.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(HttpStatus.CREATED));
        mockMvc.perform(post("/admin/roomTypes/create")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/roomTypes"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testUpdateRoomTypeForm() throws Exception {
        Mockito.when(restTemplate.getForObject(any(), eq(RoomTypeDTO.class))).thenReturn(roomTypeDTO);
        mockMvc.perform(get("/admin/roomTypes/update/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/updateRoomTypes"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testUpdateRoomTypesWithErrors() throws Exception {
        Map<String, String> mapErrors = new HashMap();
        mapErrors.put("A", "B");

        Mockito.when(restTemplate.exchange(anyString(), any(), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(mapErrors, HttpStatus.OK));
        mockMvc.perform(post("/admin/roomTypes/update")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/updateRoomTypes"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testUpdateRoomTypesWithoutErrors() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), any(), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(HttpStatus.CREATED));

        mockMvc.perform(post("/admin/roomTypes/update")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/roomTypes"))
                .andDo(print());
    }
}