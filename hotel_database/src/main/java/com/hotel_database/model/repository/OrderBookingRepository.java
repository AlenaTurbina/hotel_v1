package com.hotel_database.model.repository;

import com.hotel_domain.model.entity.OrderBooking;
import com.hotel_domain.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderBookingRepository extends JpaRepository<OrderBooking, UUID> {
    List<OrderBooking> findAllByClient(User user);
}
