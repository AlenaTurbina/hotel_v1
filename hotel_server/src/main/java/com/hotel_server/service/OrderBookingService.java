package com.hotel_server.service;

import com.hotel_domain.model.entity.OrderBooking;
import com.hotel_domain.model.entity.Room;
import com.hotel_dto.dto.OrderBookingDTO;
import com.hotel_dto.dto.RoomKindDTO;
import com.hotel_dto.dto.UserDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderBookingService {
    List<OrderBooking> getAllOrderBooking();

    OrderBooking getOrderBookingById(UUID id);

    OrderBooking saveOrderBooking(OrderBookingDTO orderBookingDTO);

    OrderBooking updateOrderBooking(OrderBookingDTO orderBookingDTO);

    Double calculationSumTotal(OrderBookingDTO orderBookingDTO, Room room);

    Room getFirstRelevantFreeRoom(OrderBookingDTO orderBookingDTO);

    Map<RoomKindDTO, Long> getRoomKindsWithFreeRooms(OrderBookingDTO orderBookingDTO);

    List<Room> getListFreeRoomsOnOrderBookingDates(OrderBooking orderBooking);

    List<OrderBooking> findAllOrderBookingsByUser(UserDTO userDTO);
}
