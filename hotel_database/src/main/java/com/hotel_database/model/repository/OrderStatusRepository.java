package com.hotel_database.model.repository;

import com.hotel_domain.model.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, UUID> {
    OrderStatus findOrderStatusByName(String name);
}
