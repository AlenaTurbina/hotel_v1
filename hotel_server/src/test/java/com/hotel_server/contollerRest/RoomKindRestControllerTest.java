package com.hotel_server.contollerRest;

import com.hotel_dto.dto.RoomKindDTO;
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

    RoomKindDTO roomKindDTO = new RoomKindDTO();
    UUID uuid = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        roomKindDTO.setId(uuid);
        roomKindDTO.setRoomTypeName("A");
        roomKindDTO.setClassApartmentName("B");
    }

    @Test
    void testGetAllRoomKinds() throws Exception {
        List<RoomKindDTO> roomKindDTOList = new ArrayList<>(List.of(roomKindDTO));
        Mockito.when(roomKindMapper.toListRoomKindDTO(any())).thenReturn(roomKindDTOList);
        mockMvc.perform(get("/api/admin/roomKinds")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].roomTypeName", Matchers.equalTo(roomKindDTO.getRoomTypeName())))
                .andExpect(content().json(asJsonString(roomKindDTOList)))
                .andDo(print());
    }

    @Test
    void testCreateRoomKind() throws Exception {
        Mockito.when(roomKindMapper.toRoomKindDTO(any())).thenReturn(roomKindDTO);
        Mockito.when(roomKindValidator.supports(any())).thenReturn(true);
        mockMvc.perform(post("/api/admin/roomKinds")
                .content(asJsonString(roomKindDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(roomKindDTO)))
                .andDo(print());
    }

    @Test
    void testPriceList() throws Exception {
        List<RoomKindDTO> roomKindDTOList = new ArrayList<>(List.of(roomKindDTO));
        Mockito.when(roomKindMapper.toListRoomKindDTO(any())).thenReturn(roomKindDTOList);
        mockMvc.perform(get("/api/uniqueRoomKindsFromRooms")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(content().json(asJsonString(roomKindDTOList)))
                .andDo(print());
    }
}