package com.hotel_server.contollerRest;

import com.hotel_dto.dto.OrderBookingDTO;
import com.hotel_dto.dto.RoomKindDTO;
import com.hotel_server.service.OrderBookingService;
import com.hotel_server.validator.DateBookingValidator;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static com.hotel_server.util.Utils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DateBookingRestController.class)
class DateBookingRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private OrderBookingService orderBookingService;
    @MockBean
    private DateBookingValidator dateBookingValidator;

    @Test
    void testGetRoomKindsWithFreeRoomsQuantity() throws Exception {
        RoomKindDTO roomKindDTO = new RoomKindDTO();
        roomKindDTO.setId(1);
        roomKindDTO.setRoomTypeName("A");
        roomKindDTO.setClassApartmentName("B");

        OrderBookingDTO orderBookingDTO = new OrderBookingDTO();
        orderBookingDTO.setRoomKind(3);

        Map<RoomKindDTO, Long> roomKindsMap = new HashMap<>();
        roomKindsMap.put(roomKindDTO, 5L);
        Mockito.when(orderBookingService.getRoomKindsWithFreeRooms(any())).thenReturn(roomKindsMap);
        Mockito.when(dateBookingValidator.supports(any())).thenReturn(true);
        mockMvc.perform(post("/api/home/freeRooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(orderBookingDTO))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(roomKindsMap)))
                .andDo(print());
    }

}