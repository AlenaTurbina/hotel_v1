package com.hotel_ui.controller;

import com.hotel_dto.dto.OrderBookingDTO;
import com.hotel_dto.dto.OrderStatusDTO;
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
class OrderBookingControllerUITest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private OrderBookingDTO orderBookingDTO;
    @MockBean
    private RoomDTO roomDTO;
    @MockBean
    private OrderStatusDTO orderStatusDTO;
    @MockBean
    private ResponseEntity<Map<String, String>> responseEntity;
    @MockBean
    private Map<String, Integer> map;


    @Test
    @WithUserDetails("admin@test.com")
    void testGetAllOrderBookings() throws Exception {
        List<OrderBookingDTO> orderBookingDTOList = new ArrayList<>(List.of(orderBookingDTO));
        Mockito.when(restTemplate.getForObject(any(), any())).thenReturn(orderBookingDTOList);

        mockMvc.perform(get("/admin/orderBookings")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/orderBookings"))
                .andDo(print());
    }

    @Test
    void testFreeRoomsForm() throws Exception {
        mockMvc.perform(get("/home/freeRoomForms")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("home/freeRoomForms"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testFreeRoomsFormAdmin() throws Exception {
        mockMvc.perform(get("/admin/freeRoomFormsAdmin")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/freeRoomFormsAdmin"))
                .andDo(print());
    }

    @Test
    void testFreeRoomsWithErrors() throws Exception {
        Map<String, String> mapErrors = new HashMap();
        mapErrors.put("A", "B");

        Mockito.when(restTemplate.postForEntity(anyString(), any(OrderBookingDTO.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(mapErrors, HttpStatus.OK));
        mockMvc.perform(post("/home/freeRoomForms")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(view().name("home/freeRoomForms"))
                .andDo(print());
    }

    @Test
    void testFreeRoomsWithoutErrors() throws Exception {
        Mockito.when(restTemplate.postForEntity(anyString(), any(OrderBookingDTO.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(HttpStatus.CREATED));
        mockMvc.perform(post("/home/freeRoomForms")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("home/freeRoomsClient"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testFreeRoomsAdminWithErrors() throws Exception {
        Map<String, String> mapErrors = new HashMap();
        mapErrors.put("A", "B");

        Mockito.when(restTemplate.postForEntity(anyString(), any(OrderBookingDTO.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(mapErrors, HttpStatus.OK));
        mockMvc.perform(post("/admin/freeRoomFormsAdmin")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(view().name("admin/freeRoomFormsAdmin"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testFreeRoomsAdminWithoutErrors() throws Exception {
        Mockito.when(restTemplate.postForEntity(anyString(), any(OrderBookingDTO.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(HttpStatus.CREATED));
        mockMvc.perform(post("/admin/freeRoomFormsAdmin")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/freeRoomsAdmin"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("client@test.com")
    void testOrderForm() throws Exception {
        mockMvc.perform(get("/client/orderForms")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("client/orderForms"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("client@test.com")
    void testCreateOrderBookingWithErrors() throws Exception {
        Map<String, String> mapErrors = new HashMap();
        mapErrors.put("A", "B");

        Mockito.when(restTemplate.postForEntity(anyString(), any(OrderBookingDTO.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(mapErrors, HttpStatus.OK));
        mockMvc.perform(post("/client/orderForms")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(view().name("client/orderForms"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("client@test.com")
    void testCreateOrderBookingWithoutErrorsResultRefusal() throws Exception {
        Mockito.when(restTemplate.postForEntity(anyString(), any(OrderBookingDTO.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(orderBookingDTO, HttpStatus.RESET_CONTENT));

        mockMvc.perform(post("/client/orderForms")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("client/orderResultRefusal"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("client@test.com")
    void testCreateOrderBookingWithoutErrorsResultInvoice() throws Exception {
        Mockito.when(map.get(anyString())).thenReturn(1);
        Mockito.when(restTemplate.postForEntity(anyString(), any(OrderBookingDTO.class), eq(Map.class))).thenReturn(new ResponseEntity(map, HttpStatus.CREATED));
        Mockito.when(restTemplate.getForObject(anyString(), eq(OrderBookingDTO.class))).thenReturn(orderBookingDTO);
        mockMvc.perform(post("/client/orderForms")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("client/orderResultInvoice"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testUpdateOrderBookingForm() throws Exception {
        List<RoomDTO> roomDTOList = new ArrayList<>(List.of(roomDTO));
        Mockito.when(restTemplate.getForObject(anyString(), eq(OrderBookingDTO.class))).thenReturn(orderBookingDTO);
        Mockito.when(restTemplate.postForEntity(anyString(), anyInt(), eq(List.class))).thenReturn(new ResponseEntity<>(roomDTOList, HttpStatus.OK));

        mockMvc.perform(get("/admin/orderBookings/update/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/updateOrderBookings"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testUpdateRoomsWithErrors() throws Exception {
        Map<String, String> mapErrors = new HashMap();
        mapErrors.put("A", "B");

        Mockito.when(restTemplate.exchange(anyString(), any(), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(mapErrors, HttpStatus.OK));
        mockMvc.perform(post("/admin/orderBookings/update")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/updateOrderBookings"))
                .andDo(print());
    }

    @Test
    @WithUserDetails("admin@test.com")
    void testUpdateRoomsWithoutErrors() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), any(), any(HttpEntity.class), eq(Map.class)))
                .thenReturn(new ResponseEntity(HttpStatus.CREATED));

        mockMvc.perform(post("/admin/orderBookings/update")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/admin/orderBookings"))
                .andDo(print());
    }
}