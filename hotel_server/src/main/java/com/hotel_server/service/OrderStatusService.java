package com.hotel_server.service;

import com.hotel_domain.model.entity.OrderStatus;

import java.util.List;
import java.util.UUID;

public interface OrderStatusService {
    List<OrderStatus> getAllOrderStatuses();

    OrderStatus getOrderStatusById(UUID id);

    OrderStatus getOrderStatusByName(String name);
}
