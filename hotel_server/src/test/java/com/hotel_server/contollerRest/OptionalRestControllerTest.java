package com.hotel_server.contollerRest;

import com.hotel_dto.dto.OptionalDTO;
import com.hotel_dto.mapper.OptionalMapper;
import com.hotel_server.service.OptionalService;
import com.hotel_server.validator.OptionalValidator;
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

@WebMvcTest(OptionalRestController.class)
class OptionalRestControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private OptionalService optionalService;
    @MockBean
    private OptionalMapper optionalMapper;
    @MockBean
    private OptionalValidator optionalValidator;

    OptionalDTO optionalDTO = new OptionalDTO();

    @BeforeEach
    public void setUp() {
        optionalDTO.setId(UUID.randomUUID());
        optionalDTO.setName("A");
        optionalDTO.setOptionalPrice(10.0);
    }

    @Test
    void testGetAllOptionals() throws Exception {
        List<OptionalDTO> optionalDTOList = new ArrayList<>(List.of(optionalDTO));
        Mockito.when(optionalMapper.toListOptionalDTO(any())).thenReturn(optionalDTOList);

        mockMvc.perform(get("/api/admin/optionals")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo(optionalDTO.getName())))
                .andExpect(content().json(asJsonString(optionalDTOList)))
                .andDo(print());
    }

    @Test
    void testCreateOptional() throws Exception {
        Mockito.when(optionalMapper.toOptionalDTO(any())).thenReturn(optionalDTO);
        Mockito.when(optionalValidator.supports(any())).thenReturn(true);

        mockMvc.perform(post("/api/admin/optionals")
                .content(asJsonString(optionalDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(optionalDTO)))
                .andDo(print());
    }
}