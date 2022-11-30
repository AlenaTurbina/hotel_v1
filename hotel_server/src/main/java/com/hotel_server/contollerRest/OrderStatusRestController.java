package com.hotel_server.contollerRest;

import com.hotel_dto.mapper.OrderStatusMapper;
import com.hotel_server.service.OrderStatusService;
import com.hotel_dto.dto.OrderStatusDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name="OrderStatus", description = "Management of Order status - getting list")
public class OrderStatusRestController {
    private OrderStatusService orderStatusService;
    private OrderStatusMapper orderStatusMapper;

    @Operation(summary = "Getting list of Order statuses")
    @GetMapping("/admin/orderStatuses")
    List<OrderStatusDto> getAllOrderStatuses() {
        return orderStatusMapper.toListOrderStatusDTO(orderStatusService.getAllOrderStatuses());
    }

}
