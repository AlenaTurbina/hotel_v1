package com.hotel_server.contollerRest;

import com.hotel_dto.dto.OrderStatusDto;
import com.hotel_dto.mapper.OrderStatusMapper;
import com.hotel_server.service.OrderStatusService;
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

@WebMvcTest(OrderStatusRestController.class)
class OrderStatusRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderStatusService orderStatusService;
    @MockBean
    private OrderStatusMapper orderStatusMapper;

    OrderStatusDto orderStatusDTO = new OrderStatusDto();
    UUID uuid = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        orderStatusDTO.setId(uuid);
    }

    @Test
    void testGetAllOrderStatuses() throws Exception {
        List<OrderStatusDto> orderStatusDtoList = new ArrayList<>(List.of(orderStatusDTO));
        Mockito.when(orderStatusMapper.toListOrderStatusDTO(any())).thenReturn(orderStatusDtoList);
        mockMvc.perform(get("/api/admin/orderStatuses")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(orderStatusDTO.getId().toString())))
                .andExpect(content().json(asJsonString(orderStatusDtoList)))
                .andDo(print());
    }
}