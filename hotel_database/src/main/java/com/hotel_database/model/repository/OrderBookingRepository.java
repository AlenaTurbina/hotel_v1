package com.hotel_database.model.repository;

import com.hotel_domain.model.entity.OrderBooking;
import com.hotel_domain.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderBookingRepository extends JpaRepository<OrderBooking, Integer> {
    List<OrderBooking> findAllByClient(User user);
}
