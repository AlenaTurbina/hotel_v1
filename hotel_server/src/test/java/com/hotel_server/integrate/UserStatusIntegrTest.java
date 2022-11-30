package com.hotel_server.integrate;

import com.hotel_dto.mapper.UserStatusMapper;
import com.hotel_domain.model.entity.UserStatus;
import com.hotel_server.service.UserStatusService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.hotel_server.util.Utils.asJsonString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan("com.hotel_dto")
class UserStatusIntegrTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private UserStatusMapper userStatusMapper;
    @MockBean
    private UserStatusService userStatusService;

    @Test
    void testGetAllUserStatuses() throws Exception {
        UserStatus userStatus = new UserStatus(UUID.randomUUID(), "A");
        UserStatus userStatus1 = new UserStatus(UUID.randomUUID(), "B");
        List<UserStatus> userStatusList = new ArrayList<>(List.of(userStatus, userStatus1));
        Mockito.when(userStatusService.getAllUserStatuses()).thenReturn(userStatusList);
        mockMvc.perform(get("/api/admin/userStatuses")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(content().json(asJsonString(userStatusMapper.toListUserStatusDto(userStatusList))))
                .andDo(print());
    }
}