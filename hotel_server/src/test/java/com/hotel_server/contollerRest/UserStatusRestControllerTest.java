package com.hotel_server.contollerRest;

import com.hotel_dto.dto.UserStatusDto;
import com.hotel_dto.mapper.UserStatusMapper;
import com.hotel_server.service.UserStatusService;
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


@WebMvcTest(UserStatusRestController.class)
class UserStatusRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserStatusService userStatusService;
    @MockBean
    private UserStatusMapper userStatusMapper;

    UserStatusDto userStatusDTO = new UserStatusDto();

    @BeforeEach
    public void setUp() {
        userStatusDTO.setId(UUID.randomUUID());
    }

    @Test
    void testGetAllUserStatuses() throws Exception {
        List<UserStatusDto> userStatusDtoList = new ArrayList<>(List.of(userStatusDTO));
        Mockito.when(userStatusMapper.toListUserStatusDto(any())).thenReturn(userStatusDtoList);
        mockMvc.perform(get("/api/admin/userStatuses")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(userStatusDTO.getId().toString())))
                .andExpect(content().json(asJsonString(userStatusDtoList)))
                .andDo(print());
    }
}