package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.OrderStatus;
import com.hotel_dto.dto.OrderStatusDTO;

import java.util.List;

public interface OrderStatusMapper {
    OrderStatusDTO toOrderStatusDTO(OrderStatus orderStatus);

    List<OrderStatusDTO> toListOrderStatusDTO(List<OrderStatus> orderStatuses);

}
