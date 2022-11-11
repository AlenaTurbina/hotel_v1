package com.hotel_server.service.impl;

import com.hotel_database.model.repository.OrderStatusRepository;
import com.hotel_domain.model.entity.OrderStatus;
import com.hotel_server.exceptionHandler.exception.ServerEntityNotFoundException;
import com.hotel_server.service.OrderStatusService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {
    private OrderStatusRepository orderStatusRepository;

    @Override
    public List<OrderStatus> getAllOrderStatuses() {
        log.info("OrderStatus getAllOrderStatuses");
        return orderStatusRepository.findAll();
    }

    @Override
    public OrderStatus getOrderStatusById(UUID id) {
        log.info("Get OrderStatus by id " + id);
        return orderStatusRepository.findById(id).orElseThrow(() -> new ServerEntityNotFoundException(id));
    }

    @Override
    public OrderStatus getOrderStatusByName(String name) {
        OrderStatus orderStatus = orderStatusRepository.findOrderStatusByName(name);
        if (orderStatus != null) {
            log.info("OrderStatus getByName is not null (name): " + name);
            return orderStatus;
        } else {
            log.info("OrderStatus getByName is null (name): " + name);
            return null;
        }
    }


}
