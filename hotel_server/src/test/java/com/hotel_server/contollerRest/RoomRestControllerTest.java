package com.hotel_server.contollerRest;

import com.hotel_dto.dto.RoomDTO;
import com.hotel_dto.mapper.ClassApartmentMapper;
import com.hotel_dto.mapper.RoomKindMapper;
import com.hotel_dto.mapper.RoomMapper;
import com.hotel_dto.mapper.RoomTypeMapper;
import com.hotel_server.service.RoomService;
import com.hotel_server.validator.RoomValidator;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoomRestController.class)
class RoomRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private RoomService roomService;
    @MockBean
    private RoomMapper roomMapper;
    @MockBean
    private RoomKindMapper roomKindMapper;
    @MockBean
    private RoomTypeMapper roomTypeMapper;
    @MockBean
    private ClassApartmentMapper classApartmentMapper;
    @MockBean
    private RoomValidator roomValidator;

    RoomDTO roomDTO = new RoomDTO();

    @BeforeEach
    public void setUp() {
        roomDTO.setId(UUID.randomUUID());
        roomDTO.setName("1A");
        roomDTO.setRoomKind(UUID.randomUUID());
    }

    @Test
    void testGetAllRooms() throws Exception {
        List<RoomDTO> roomDTOList = new ArrayList<>(List.of(roomDTO));
        Mockito.when(roomMapper.toListRoomDTO(any())).thenReturn(roomDTOList);
        mockMvc.perform(get("/api/admin/rooms")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(content().json(asJsonString(roomDTOList)))
                .andDo(print());
    }

    //This function was moved into another microservice
//    @Test
//    void testCreateRoom() throws Exception {
//        Mockito.when(roomMapper.toRoomDTO(any())).thenReturn(roomDTO);
//        Mockito.when(roomValidator.supports(any())).thenReturn(true);
//        mockMvc.perform(post("/api/admin/rooms")
//                        .content(asJsonString(roomDTO))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(content().json(asJsonString(roomDTO)))
//                .andDo(print());
//    }
}