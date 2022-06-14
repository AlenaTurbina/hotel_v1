package com.hotel_server.contollerRest;

import com.hotel_dto.mapper.OrderStatusMapper;
import com.hotel_server.service.OrderStatusService;
import com.hotel_dto.dto.OrderStatusDTO;
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

    @Test
    void testGetAllOrderStatuses() throws Exception {
        Integer id = 1;
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
        orderStatusDTO.setId(id);

        List<OrderStatusDTO> orderStatusDTOList = new ArrayList<>(List.of(orderStatusDTO));
        Mockito.when(orderStatusMapper.toListOrderStatusDTO(any())).thenReturn(orderStatusDTOList);

        mockMvc.perform(get("/api/admin/orderStatuses")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.equalTo(orderStatusDTO.getId())))
                .andExpect(content().json(asJsonString(orderStatusDTOList)))
                .andDo(print());
    }
}