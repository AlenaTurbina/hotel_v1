package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.OrderBooking;
import com.hotel_dto.dto.OrderBookingDTO;

import java.util.List;

public interface OrderBookingMapper {
    OrderBookingDTO toOrderBookingDTO(OrderBooking orderBooking);

    List<OrderBookingDTO> toListOrderBookingDTO(List<OrderBooking> orderBookings);

}
