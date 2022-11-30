package com.hotel_server.contollerRest;

import com.hotel_dto.dto.RoomKindDto;
import com.hotel_dto.mapper.RoomKindMapper;
import com.hotel_server.service.RoomKindService;
import com.hotel_server.validator.RoomKindUpdateValidator;
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

@WebMvcTest(RoomKindUpdateRestController.class)
class RoomKindUpdateRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private RoomKindService roomKindService;
    @MockBean
    private RoomKindMapper roomKindMapper;
    @MockBean
    private RoomKindUpdateValidator roomKindUpdateValidator;

    RoomKindDto roomKindDTO = new RoomKindDto();
    UUID uuid = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        roomKindDTO.setId(uuid);
        roomKindDTO.setRoomTypeName("A");
        roomKindDTO.setClassApartmentName("B");
    }

    @Test
    void testGetRoomKind() throws Exception {
        Mockito.when(roomKindMapper.toRoomKindDTO(any())).thenReturn(roomKindDTO);
        mockMvc.perform(get("/api/admin/roomKinds/{id}", uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(roomKindDTO)))
                .andDo(print());
    }

    @Test
    void testUpdateRoomKind() throws Exception {
        Mockito.when(roomKindMapper.toRoomKindDTO(any())).thenReturn(roomKindDTO);
        Mockito.when(roomKindUpdateValidator.supports(any())).thenReturn(true);
        mockMvc.perform(put("/api/admin/roomKinds/")
                .content(asJsonString(roomKindDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(roomKindDTO)))
                .andDo(print());
    }
}