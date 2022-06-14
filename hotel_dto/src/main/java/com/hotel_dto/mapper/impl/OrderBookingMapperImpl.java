package com.hotel_dto.mapper.impl;

import com.hotel_domain.model.entity.OrderBooking;
import com.hotel_dto.dto.OrderBookingDTO;
import com.hotel_dto.mapper.OrderBookingMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderBookingMapperImpl implements OrderBookingMapper {

    @Override
    public OrderBookingDTO toOrderBookingDTO(OrderBooking orderBooking) {
        OrderBookingDTO orderBookingDTO = new OrderBookingDTO();
        orderBookingDTO.setId(orderBooking.getId());
        orderBookingDTO.setDate(orderBooking.getDate());
        orderBookingDTO.setDateArrival(orderBooking.getDateArrival());
        orderBookingDTO.setDateDeparture(orderBooking.getDateDeparture());
        orderBookingDTO.setUserFirstName(orderBooking.getClient().getFirstName());
        orderBookingDTO.setUserLastName(orderBooking.getClient().getLastName());
        orderBookingDTO.setQuantityPersons(orderBooking.getQuantityPersons());
        orderBookingDTO.setRoomTypeName(orderBooking.getRoom().getRoomKind().getRoomType().getName());
        orderBookingDTO.setClassApartmentName(orderBooking.getRoom().getRoomKind().getClassApartment().getName());
        orderBookingDTO.setRoomName(orderBooking.getRoom().getName());
        orderBookingDTO.setSumTotal(orderBooking.getSumTotal());
        orderBookingDTO.setOrderStatusName(orderBooking.getOrderStatus().getName());
        return orderBookingDTO;
    }

    @Override
    public List<OrderBookingDTO> toListOrderBookingDTO(List<OrderBooking> orderBookings) {
        List<OrderBookingDTO> orderBookingsDTO = new ArrayList<>();
        for (OrderBooking orderBooking : orderBookings) {
            orderBookingsDTO.add(toOrderBookingDTO(orderBooking));
        }
        return orderBookingsDTO;
    }

}
