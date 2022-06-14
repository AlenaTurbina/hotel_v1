package com.hotel_ui.controller;

import com.hotel_dto.dto.RoomDTO;
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
class RoomControllerUITest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private RoomDTO roomDTO;

    @Test
    @WithUserDetails("admin@test.com")
    void testGetAllRooms() throws Exception {
        List<RoomDTO> roomDTOList = new ArrayList<>(List.of(roomDTO));
        Mockito.when(restTemplate.getForObject(any(), any())).thenReturn(roomDTOList);

        mockMvc.perform(get("/admin/rooms")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/rooms"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testCreateRoomForm() throws Exception {
        mockMvc.perform(get("/admin/rooms/create")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/createRooms"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testCreateRoomWithErrors() throws Exception {
        Map<String, String> mapErrors = new HashMap();
        mapErrors.put("A", "B");

        Mockito.when(restTemplate.postForEntity(anyString(), any(RoomDTO.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(mapErrors, HttpStatus.OK));
        mockMvc.perform(post("/admin/rooms/create")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(view().name("admin/createRooms"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testCreateRoomWithoutErrors() throws Exception {
        Mockito.when(restTemplate.postForEntity(anyString(), any(RoomDTO.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(HttpStatus.CREATED));
        mockMvc.perform(post("/admin/rooms/create")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/rooms"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testUpdateRoomForm() throws Exception {
        Mockito.when(restTemplate.getForObject(any(), eq(RoomDTO.class))).thenReturn(roomDTO);
        mockMvc.perform(get("/admin/rooms/update/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/updateRooms"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testUpdateRoomsWithErrors() throws Exception {
        Map<String, String> mapErrors = new HashMap();
        mapErrors.put("A", "B");

        Mockito.when(restTemplate.exchange(anyString(), any(), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(mapErrors, HttpStatus.OK));
        mockMvc.perform(post("/admin/rooms/update")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/updateRooms"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testUpdateRoomsWithoutErrors() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), any(), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(HttpStatus.CREATED));

        mockMvc.perform(post("/admin/rooms/update")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/rooms"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("client@test.com")
     void testPriceList() throws Exception {
        mockMvc.perform(get("/home/priceList")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("home/priceList"))
                .andDo(print());
    }

    @Test
    void testShowRoomCards() throws Exception {
        mockMvc.perform(get("/home/roomCards")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("home/roomCards"))
                .andDo(print());
    }
}