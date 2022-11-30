package com.hotel_server.service;

import com.hotel_domain.model.entity.OrderBooking;
import com.hotel_domain.model.entity.Room;
import com.hotel_dto.dto.OrderBookingDto;
import com.hotel_dto.dto.RoomKindDto;
import com.hotel_dto.dto.UserDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderBookingService {
    List<OrderBooking> getAllOrderBooking();

    OrderBooking getOrderBookingById(UUID id);

    OrderBooking saveOrderBooking(OrderBookingDto orderBookingDTO);

    OrderBooking updateOrderBooking(OrderBookingDto orderBookingDTO);

    Double calculationSumTotal(OrderBookingDto orderBookingDTO, Room room);

    Room getFirstRelevantFreeRoom(OrderBookingDto orderBookingDTO);

    Map<RoomKindDto, Long> getRoomKindsWithFreeRooms(OrderBookingDto orderBookingDTO);

    List<Room> getListFreeRoomsOnOrderBookingDates(OrderBooking orderBooking);

    List<OrderBooking> findAllOrderBookingsByUser(UserDto userDTO);
}
