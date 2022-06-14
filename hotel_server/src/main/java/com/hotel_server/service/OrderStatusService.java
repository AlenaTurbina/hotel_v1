package com.hotel_server.service;

import com.hotel_domain.model.entity.OrderStatus;

import java.util.List;

public interface OrderStatusService {
    List<OrderStatus> getAllOrderStatuses();

    OrderStatus getOrderStatusById(Integer id);

    OrderStatus getOrderStatusByName(String name);
}
