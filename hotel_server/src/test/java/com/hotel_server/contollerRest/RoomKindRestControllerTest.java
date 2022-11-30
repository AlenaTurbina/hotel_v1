package com.hotel_server.contollerRest;

import com.hotel_dto.dto.RoomKindDto;
import com.hotel_dto.mapper.RoomKindMapper;
import com.hotel_server.service.RoomKindService;
import com.hotel_server.validator.RoomKindValidator;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.hotel_server.util.Utils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoomKindRestController.class)
class RoomKindRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private RoomKindService roomKindService;
    @MockBean
    private RoomKindMapper roomKindMapper;
    @MockBean
    private RoomKindValidator roomKindValidator;


    RoomKindDto roomKindDto = new RoomKindDto();
    UUID uuid = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        roomKindDto.setId(uuid);
        roomKindDto.setRoomTypeName("A");
        roomKindDto.setClassApartmentName("B");
    }

    @Test
    void testGetAllRoomKinds() throws Exception {
        List<RoomKindDto> roomKindDtoList = new ArrayList<>(List.of(roomKindDto));
        Mockito.when(roomKindMapper.toListRoomKindDto(any())).thenReturn(roomKindDtoList);
        mockMvc.perform(get("/api/admin/roomKinds")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].roomTypeName", Matchers.equalTo(roomKindDto.getRoomTypeName())))
                .andExpect(content().json(asJsonString(roomKindDtoList)))
                .andDo(print());
    }

    @Test
    void testCreateRoomKind() throws Exception {
        Mockito.when(roomKindMapper.toRoomKindDTO(any())).thenReturn(roomKindDto);
        Mockito.when(roomKindValidator.supports(any())).thenReturn(true);
        mockMvc.perform(post("/api/admin/roomKinds")
                .content(asJsonString(roomKindDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(roomKindDto)))
                .andDo(print());
    }

    @Test
    void testPriceList() throws Exception {
        List<RoomKindDto> roomKindDtoList = new ArrayList<>(List.of(roomKindDto));
        Mockito.when(roomKindMapper.toListRoomKindDto(any())).thenReturn(roomKindDtoList);
        mockMvc.perform(get("/api/uniqueRoomKindsFromRooms")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(content().json(asJsonString(roomKindDtoList)))
                .andDo(print());
    }
}