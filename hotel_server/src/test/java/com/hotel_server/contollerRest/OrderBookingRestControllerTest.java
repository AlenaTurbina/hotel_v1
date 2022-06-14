package com.hotel_server.contollerRest;

import com.hotel_dto.mapper.OrderBookingMapper;
import com.hotel_dto.mapper.RoomKindMapper;
import com.hotel_dto.mapper.RoomMapper;
import com.hotel_domain.model.entity.OrderBooking;
import com.hotel_server.service.OrderBookingService;
import com.hotel_server.service.RoomService;
import com.hotel_server.service.impl.EmailSenderServiceImpl;
import com.hotel_server.validator.OrderBookingValidator;
import com.hotel_dto.dto.OrderBookingDTO;
import com.hotel_dto.dto.RoomDTO;
import com.hotel_dto.dto.UserDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderBookingRestController.class)
class OrderBookingRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private OrderBookingService orderBookingService;
    @MockBean
    private RoomService roomService;
    @MockBean
    private OrderBookingMapper orderBookingMapper;
    @MockBean
    private RoomMapper roomMapper;
    @MockBean
    private RoomKindMapper roomKindMapper;
    @MockBean
    private EmailSenderServiceImpl emailSenderService;
    @MockBean
    private OrderBookingValidator orderBookingValidator;

    @Test
    void testGetAllOrders() throws Exception {
        OrderBookingDTO orderBookingDTO = new OrderBookingDTO();
        orderBookingDTO.setRoomKind(3);

        List<OrderBookingDTO> orderBookingDTOList = new ArrayList<>(List.of(orderBookingDTO));
        Mockito.when(orderBookingMapper.toListOrderBookingDTO(any())).thenReturn(orderBookingDTOList);

        mockMvc.perform(get("/api/admin/orderBookings")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].roomKind", Matchers.equalTo(orderBookingDTO.getRoomKind())))
                .andExpect(content().json(asJsonString(orderBookingDTOList)))
                .andDo(print());
    }

    @Test
    void testRegisterOrderBookingSuccessfulAndExpectStatusCreated() throws Exception {
        OrderBookingDTO orderBookingDTO = new OrderBookingDTO();
        orderBookingDTO.setRoomKind(3);

        OrderBooking orderBooking = new OrderBooking();
        Mockito.when(orderBookingService.saveOrderBooking(any())).thenReturn(orderBooking);

        Mockito.when(orderBookingMapper.toOrderBookingDTO(any())).thenReturn(orderBookingDTO);
        Mockito.when(orderBookingValidator.supports(any())).thenReturn(true);
        mockMvc.perform(post("/api/orderBooking/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(orderBookingDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(orderBookingDTO)))
                .andDo(print());

    }

    @Test
    void testRegisterOrderBookingNoRoomAndExpectStatusReset() throws Exception {
        OrderBookingDTO orderBookingDTO = new OrderBookingDTO();
        orderBookingDTO.setRoomKind(3);

        Mockito.when(orderBookingMapper.toOrderBookingDTO(any())).thenReturn(orderBookingDTO);
        Mockito.when(orderBookingValidator.supports(any())).thenReturn(true);
        mockMvc.perform(post("/api/orderBooking/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(orderBookingDTO)))
                .andExpect(status().isResetContent())
                .andDo(print());
    }

    @Test
    void testGetOrderBookingForUser() throws Exception {
        Integer id = 3;
        UserDTO userDTO = new UserDTO();
        userDTO.setId(id);

        OrderBookingDTO orderBookingDTO = new OrderBookingDTO();
        orderBookingDTO.setId(id);
        List<OrderBookingDTO> orderBookingDTOList = new ArrayList<>(List.of(orderBookingDTO));

        Mockito.when(orderBookingMapper.toListOrderBookingDTO(any())).thenReturn(orderBookingDTOList);
        mockMvc.perform(post("/api/orderBooking/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(orderBookingDTOList)))
                .andDo(print());
    }

    @Test
    void testGetOrderBooking() throws Exception {
        Integer id = 1;
        OrderBookingDTO orderBookingDTO = new OrderBookingDTO();
        orderBookingDTO.setId(id);

        Mockito.when(orderBookingMapper.toOrderBookingDTO(any())).thenReturn(orderBookingDTO);
        mockMvc.perform(get("/api/admin/orderBookings/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(orderBookingDTO)))
                .andDo(print());
    }

    @Test
    void testUpdateOrderBooking() throws Exception {
        Integer id = 1;
        OrderBookingDTO orderBookingDTO = new OrderBookingDTO();
        orderBookingDTO.setId(id);

        Mockito.when(orderBookingMapper.toOrderBookingDTO(any())).thenReturn(orderBookingDTO);
        Mockito.when(orderBookingValidator.supports(any())).thenReturn(true);
        mockMvc.perform(put("/api/admin/orderBookings")
                        .content(asJsonString(orderBookingDTO))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(orderBookingDTO)))
                .andDo(print());

    }

    @Test
    void testGetRoomsOnOrderBookingDate() throws Exception {
        Integer id = 1;
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName("1A");
        roomDTO.setRoomKind(2);
        List<RoomDTO> roomDTOList = new ArrayList<>(List.of(roomDTO));

        Mockito.when(roomMapper.toListRoomDTO(any())).thenReturn(roomDTOList);
        mockMvc.perform(post("/api/roomsForDates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(id)))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(roomDTOList)))
                .andDo(print());
    }
}