package com.hotel_server.contollerRest;

import com.hotel_dto.dto.RoomTypeDto;
import com.hotel_dto.mapper.RoomTypeMapper;
import com.hotel_server.service.RoomTypeService;
import com.hotel_server.validator.RoomTypeUpdateValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static com.hotel_server.util.Utils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomTypeUpdateRestController.class)
class RoomTypeUpdateRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private RoomTypeService roomTypeService;
    @MockBean
    private RoomTypeMapper roomTypeMapper;
    @MockBean
    private RoomTypeUpdateValidator roomTypeUpdateValidator;

    RoomTypeDto roomTypeDTO = new RoomTypeDto();
    UUID uuid = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        roomTypeDTO.setId(uuid);
        roomTypeDTO.setName("A");
        roomTypeDTO.setQuantityPlaces(1);
    }

    @Test
    void testGetRoomType() throws Exception {
        Mockito.when(roomTypeMapper.toRoomTypeDto(any())).thenReturn(roomTypeDTO);
        mockMvc.perform(get("/api/admin/roomTypes/{id}", uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(roomTypeDTO)))
                .andDo(print());
    }

    @Test
    void testUpdateRoomType() throws Exception {
        Mockito.when(roomTypeMapper.toRoomTypeDto(any())).thenReturn(roomTypeDTO);
        Mockito.when(roomTypeUpdateValidator.supports(any())).thenReturn(true);
        mockMvc.perform(put("/api/admin/roomTypes/")
                .content(asJsonString(roomTypeDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(roomTypeDTO)))
                .andDo(print());
    }
}