package com.hotel_server.contollerRest;

import com.hotel_dto.dto.RoomDto;
import com.hotel_dto.mapper.RoomMapper;
import com.hotel_server.service.RoomService;
import com.hotel_server.validator.RoomUpdateValidator;
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

@WebMvcTest(RoomUpdateRestController.class)
class RoomUpdateRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private RoomService roomService;
    @MockBean
    private RoomMapper roomMapper;
    @MockBean
    private RoomUpdateValidator roomUpdateValidator;

    RoomDto roomDTO = new RoomDto();
    UUID uuid = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        roomDTO.setId(uuid);
        roomDTO.setName("1A");
        roomDTO.setRoomKindId(UUID.randomUUID());
    }

    @Test
    void testGetRoom() throws Exception {
        Mockito.when(roomMapper.toRoomDTO(any())).thenReturn(roomDTO);
        mockMvc.perform(get("/api/admin/rooms/{id}", uuid)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(roomDTO)))
                .andDo(print());
    }

    @Test
    void testUpdateRoom() throws Exception {
        Mockito.when(roomMapper.toRoomDTO(any())).thenReturn(roomDTO);
        Mockito.when(roomUpdateValidator.supports(any())).thenReturn(true);
        mockMvc.perform(put("/api/admin/rooms")
                .content(asJsonString(roomDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(roomDTO)))
                .andDo(print());
    }
}