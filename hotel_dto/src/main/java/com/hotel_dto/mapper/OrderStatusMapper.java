package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.OrderStatus;
import com.hotel_dto.dto.OrderStatusDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderStatusMapper {
    OrderStatusDto toOrderStatusDto(OrderStatus orderStatus);

    List<OrderStatusDto> toListOrderStatusDTO(List<OrderStatus> orderStatuses);

}
