package com.hotel_server.contollerRest;

import com.hotel_dto.mapper.RoomTypeMapper;
import com.hotel_server.service.RoomTypeService;
import com.hotel_server.validator.RoomTypeValidator;
import com.hotel_dto.dto.RoomTypeDTO;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoomTypeRestController.class)
class RoomTypeRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RoomTypeService roomTypeService;
    @MockBean
    private RoomTypeMapper roomTypeMapper;
    @MockBean
    private RoomTypeValidator roomTypeValidator;


//    @Test
//    void testGetAllRoomTypes() throws Exception {
//        RoomTypeDTO roomTypeDTO = new RoomTypeDTO();
//        roomTypeDTO.setId(1);
//        roomTypeDTO.setName("A");
//        roomTypeDTO.setQuantityPlaces(1);
//
//        List<RoomTypeDTO> roomTypeDTOList = new ArrayList<>(List.of(roomTypeDTO));
//        Mockito.when(roomTypeMapper.toListRoomTypeDTO(any())).thenReturn(roomTypeDTOList);
//
//        mockMvc.perform(get("/api/admin/roomTypes")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", Matchers.hasSize(1)))
//                .andExpect(jsonPath("$[0].name", Matchers.equalTo(roomTypeDTO.getName())))
//                .andExpect(content().json(asJsonString(roomTypeDTOList)))
//                .andDo(print());
//    }
//
//    @Test
//    void testCreateRoomType() throws Exception {
//        RoomTypeDTO roomTypeDTO = new RoomTypeDTO();
//        roomTypeDTO.setId(1);
//        roomTypeDTO.setName("A");
//        roomTypeDTO.setQuantityPlaces(1);
//
//        Mockito.when(roomTypeMapper.toRoomTypeDTO(any())).thenReturn(roomTypeDTO);
//        Mockito.when(roomTypeValidator.supports(any())).thenReturn(true);
//        mockMvc.perform(post("/api/admin/roomTypes")
//                        .content(asJsonString(roomTypeDTO))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
//                .andExpect(content().json(asJsonString(roomTypeDTO)))
//                .andDo(print());
//
//    }
//
//    @Test
//    void testShowUniqueRoomTypesFromRooms() throws Exception {
//        RoomTypeDTO roomTypeDTO = new RoomTypeDTO();
//        roomTypeDTO.setId(1);
//        roomTypeDTO.setName("A");
//        roomTypeDTO.setQuantityPlaces(1);
//
//        List<RoomTypeDTO> roomTypeDTOList = new ArrayList<>(List.of(roomTypeDTO));
//        Mockito.when(roomTypeMapper.toListRoomTypeDTO(any())).thenReturn(roomTypeDTOList);
//
//        mockMvc.perform(get("/api/uniqueRoomTypesFromRooms")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", Matchers.hasSize(1)))
//                .andExpect(content().json(asJsonString(roomTypeDTOList)))
//                .andDo(print());
//    }
}