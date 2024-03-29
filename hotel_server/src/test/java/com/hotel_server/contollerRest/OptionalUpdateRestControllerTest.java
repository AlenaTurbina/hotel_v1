package com.hotel_server.contollerRest;

import com.hotel_dto.dto.OptionalDto;
import com.hotel_dto.mapper.OptionalMapper;
import com.hotel_server.service.OptionalService;
import com.hotel_server.validator.OptionalUpdateValidator;
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

@WebMvcTest(OptionalUpdateRestController.class)
class OptionalUpdateRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private OptionalService optionalService;
    @MockBean
    private OptionalMapper optionalMapper;
    @MockBean
    private OptionalUpdateValidator optionalUpdateValidator;

    OptionalDto optionalDTO = new OptionalDto();
    UUID uuid = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        optionalDTO.setId(uuid);
        optionalDTO.setName("A");
        optionalDTO.setOptionalPrice(10.0);
    }

    @Test
    void getOptional() throws Exception {
        Mockito.when(optionalMapper.toOptionalDto(any())).thenReturn(optionalDTO);
        mockMvc.perform(get("/api/admin/optionals/{id}", uuid))
                .andExpect(status().isOk())
                .andExpect(content().json(asJsonString(optionalDTO)))
                .andDo(print());
    }

    @Test
    void updateOptional() throws Exception {
        Mockito.when(optionalMapper.toOptionalDto(any())).thenReturn(optionalDTO);
        Mockito.when(optionalUpdateValidator.supports(any())).thenReturn(true);
        mockMvc.perform(put("/api/admin/optionals/")
                .content(asJsonString(optionalDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(optionalDTO)))
                .andDo(print());
    }
}