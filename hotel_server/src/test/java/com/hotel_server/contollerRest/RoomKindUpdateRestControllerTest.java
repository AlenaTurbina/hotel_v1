package com.hotel_server.contollerRest;

import com.hotel_dto.mapper.RoomKindMapper;
import com.hotel_server.service.RoomKindService;
import com.hotel_server.validator.RoomKindUpdateValidator;
import com.hotel_dto.dto.RoomKindDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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


    @Test
    void testGetRoomKind() throws Exception {
        RoomKindDTO roomKindDTO = new RoomKindDTO();
        roomKindDTO.setId(1);
        roomKindDTO.setRoomTypeName("A");
        roomKindDTO.setClassApartmentName("B");

        Mockito.when(roomKindMapper.toRoomKindDTO(any())).thenReturn(roomKindDTO);
        mockMvc.perform(get("/api/admin/roomKinds/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(roomKindDTO)))
                .andDo(print());

    }

    @Test
    void testUpdateRoomKind() throws Exception {
        RoomKindDTO roomKindDTO = new RoomKindDTO();
        roomKindDTO.setId(1);
        roomKindDTO.setRoomTypeName("A");
        roomKindDTO.setClassApartmentName("B");

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