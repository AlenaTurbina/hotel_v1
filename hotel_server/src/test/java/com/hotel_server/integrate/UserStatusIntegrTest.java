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
    @MockBean
    private UserStatusService userStatusService;
    @Autowired
    private UserStatusMapper userStatusMapper;

    @Test
    void testGetAllUserStatuses() throws Exception {
        UserStatus userStatus = new UserStatus(1, null);
        List<UserStatus> userStatusList = new ArrayList<>(List.of(userStatus));
        Mockito.when(userStatusService.getAllUserStatuses()).thenReturn(userStatusList);
        mockMvc.perform(get("/api/admin/userStatuses")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(content().json(asJsonString(userStatusMapper.toListUserStatusDTO(userStatusList))))
                .andDo(print());
    }
}