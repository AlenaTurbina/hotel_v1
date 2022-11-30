package com.hotel_ui.controller;

import com.hotel_dto.dto.OrderStatusDto;
import com.hotel_ui.configuration.TestConfigurationUserDetails;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest(classes = TestConfigurationUserDetails.class)
@AutoConfigureMockMvc
class OrderStatusControllerUITest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private RestTemplate restTemplate;
    @MockBean
    private OrderStatusDto orderStatusDTO;


    @Test
    @WithUserDetails("admin@test.com")
    void testGetAllOrderStatuses() throws Exception {
        List<OrderStatusDto> orderStatusDtoList = new ArrayList<>(List.of(orderStatusDTO));
        Mockito.when(restTemplate.getForObject(any(), any())).thenReturn(orderStatusDtoList);

        mockMvc.perform(get("/admin/orderStatuses")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/orderStatuses"))
                .andDo(print());
    }
}