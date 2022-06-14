package com.hotel_dto.mapper.impl;

import com.hotel_domain.model.entity.OrderStatus;
import com.hotel_dto.dto.OrderStatusDTO;
import com.hotel_dto.mapper.OrderStatusMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderStatusMapperImpl implements OrderStatusMapper {

    @Override
    public OrderStatusDTO toOrderStatusDTO(OrderStatus orderStatus) {
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
        orderStatusDTO.setId(orderStatus.getId());
        orderStatusDTO.setName(orderStatus.getName());
        return orderStatusDTO;
    }

    @Override
    public List<OrderStatusDTO> toListOrderStatusDTO(List<OrderStatus> orderStatuses) {
        List<OrderStatusDTO> orderStatusesDTO = new ArrayList<>();
        for (OrderStatus orderStatus : orderStatuses) {
            orderStatusesDTO.add(toOrderStatusDTO(orderStatus));
        }
        return orderStatusesDTO;
    }

}