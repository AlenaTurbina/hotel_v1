package com.hotel_server.contollerRest;

import com.hotel_dto.dto.ClassApartmentDTO;
import com.hotel_dto.mapper.ClassApartmentMapper;
import com.hotel_server.service.ClassApartmentService;
import com.hotel_server.validator.ClassApartmentValidator;
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


@WebMvcTest(ClassApartmentRestController.class)
class ClassApartmentRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClassApartmentService classApartmentService;
    @MockBean
    private ClassApartmentValidator classApartmentValidator;
    @MockBean
    private ClassApartmentMapper classApartmentMapper;

    ClassApartmentDTO classApartmentDTO = new ClassApartmentDTO();

    @BeforeEach
    public void setUp() {
        ClassApartmentDTO classApartmentDTO = new ClassApartmentDTO();
        classApartmentDTO.setId(UUID.randomUUID());
        classApartmentDTO.setName("A");
        classApartmentDTO.setPlacePrice(10.0);
    }

    @Test
    void testGetAllClassApartments() throws Exception {
        classApartmentDTO.setId(UUID.randomUUID());
        List<ClassApartmentDTO> classApartmentDTOList = new ArrayList<>(List.of(classApartmentDTO));
        Mockito.when(classApartmentMapper.toListClassApartmentDTO(any())).thenReturn(classApartmentDTOList);

        mockMvc.perform(get("/api/admin/classApartments")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo(classApartmentDTO.getName())))
                .andExpect(content().json(asJsonString(classApartmentDTOList)))
                .andDo(print());
    }

    @Test
    void testCreateClassApartment() throws Exception {
        Mockito.when(classApartmentMapper.toClassApartmentDTO(any())).thenReturn(classApartmentDTO);
        Mockito.when(classApartmentValidator.supports(any())).thenReturn(true);

        mockMvc.perform(post("/api/admin/classApartments")
                .content(asJsonString(classApartmentDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(classApartmentDTO)))
                .andDo(print());
    }

    @Test
    void testShowUniqueClassApartmentsFromRooms() throws Exception {
        List<ClassApartmentDTO> classApartmentDTOList = new ArrayList<>(List.of(classApartmentDTO));
        Mockito.when(classApartmentMapper.toListClassApartmentDTO(any())).thenReturn(classApartmentDTOList);

        mockMvc.perform(get("/api/uniqueClassApartmentsFromRooms")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(content().json(asJsonString(classApartmentDTOList)))
                .andDo(print());
    }
}