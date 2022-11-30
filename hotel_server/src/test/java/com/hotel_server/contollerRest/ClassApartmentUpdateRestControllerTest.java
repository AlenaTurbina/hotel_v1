package com.hotel_server.contollerRest;

import com.hotel_dto.dto.ClassApartmentDto;
import com.hotel_dto.mapper.ClassApartmentMapper;
import com.hotel_server.service.ClassApartmentService;
import com.hotel_server.validator.ClassApartmentUpdateValidator;
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

@WebMvcTest(ClassApartmentUpdateRestController.class)
class ClassApartmentUpdateRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ClassApartmentService classApartmentService;
    @MockBean
    private ClassApartmentMapper classApartmentMapper;
    @MockBean
    private ClassApartmentUpdateValidator classApartmentUpdateValidator;

    ClassApartmentDto classApartmentDTO = new ClassApartmentDto();
    UUID uuid = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        classApartmentDTO.setId(uuid);
        classApartmentDTO.setName("A");
        classApartmentDTO.setPlacePrice(10.0);
    }

    @Test
    void testGetClassApartment() throws Exception {
        Mockito.when(classApartmentMapper.toClassApartmentDto(any())).thenReturn(classApartmentDTO);
        mockMvc.perform(get("/api/admin/classApartments/{id}", uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(classApartmentDTO)))
                .andDo(print());
    }

    @Test
    void testUpdateClassApartment() throws Exception {
        Mockito.when(classApartmentMapper.toClassApartmentDto(any())).thenReturn(classApartmentDTO);
        Mockito.when(classApartmentUpdateValidator.supports(any())).thenReturn(true);
        mockMvc.perform(put("/api/admin/classApartments/")
                .content(asJsonString(classApartmentDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(classApartmentDTO)))
                .andDo(print());
    }
}