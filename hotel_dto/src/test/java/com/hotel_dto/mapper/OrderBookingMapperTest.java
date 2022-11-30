package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.*;
import com.hotel_dto.dto.OrderBookingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ComponentScan("com.hotel_domain")
public class OrderBookingMapperTest {
    private OrderBookingMapper orderBookingMapper = Mappers.getMapper(OrderBookingMapper.class);
    public static final String id1 = "788c07d7-4275-4101-8fc3-1d77986f2129";
    public static final String id2 = "888c07d7-4275-4101-8fc3-1d77986f2129";
    public static final String id3 = "988c07d7-4275-4101-8fc3-1d77986f2129";
    public static final String id4 = "188c07d7-4275-4101-8fc3-1d77986f2129";
    public static final String id5 = "288c07d7-4275-4101-8fc3-1d77986f2129";
    public static final String id6 = "388c07d7-4275-4101-8fc3-1d77986f2129";
    public static final String id7 = "488c07d7-4275-4101-8fc3-1d77986f2129";
    public static final String id8 = "588c07d7-4275-4101-8fc3-1d77986f2129";
    public static final String id9 = "688c07d7-4275-4101-8fc3-1d77986f2129";
    public static final String id10 = "698c07d7-4275-4101-8fc3-1d77986f2129";

    OrderStatus orderStatus;
    ClassApartment classApartment;
    RoomType roomType;
    RoomKind roomKind;
    Room room;
    User user;
    UserStatus userStatus;
    Role role;
    Optional optional;
    List<Optional> optionals;
    OrderBooking orderBooking;

    @BeforeEach
    void setUp() {
        orderStatus = new OrderStatus(UUID.fromString(id1), "A");
        classApartment = new ClassApartment(UUID.fromString(id2), "B", 10.0);
        roomType = new RoomType(UUID.fromString(id3), "A", 1);
        roomKind = new RoomKind(UUID.fromString(id4), 10.0, roomType, classApartment);
        List<OrderBooking> emptyList = Collections.emptyList();
        room = new Room(UUID.fromString(id5), "AAA", roomKind, emptyList);
        userStatus = new UserStatus(UUID.fromString(id6), "A");
        role = new Role(UUID.fromString(id7), "B");
        user = new User(UUID.fromString(id8), "user@user.com", "123",
                "A", "B", "+123123123", "AA123",
                userStatus, emptyList, new ArrayList<>(List.of(role)));
        optional = new Optional(UUID.fromString(id9), "A", 10.0);
        optionals = new ArrayList<>(List.of(optional));
        orderBooking = new OrderBooking(UUID.fromString(id10), null, null, null,
                5, user, room, 150.0, orderStatus, optionals);
    }

    @Test
    public void givenToOrderBookingDto_whenMapper_ThenCorrect() {
        OrderBookingDto orderBookingDto = orderBookingMapper.toOrderBookingDto(orderBooking);

        assertEquals(orderBooking.getId(), orderBookingDto.getId());
        assertEquals(orderBooking.getRoom().getRoomKind().getClassApartment().getId(), orderBookingDto.getClassApartmentId());
        assertEquals(orderBooking.getRoom().getRoomKind().getRoomType().getId(), orderBookingDto.getRoomTypeId());
        assertEquals(orderBooking.getRoom().getRoomKind().getRoomType().getName(), orderBookingDto.getRoomTypeName());
        assertEquals(orderBooking.getRoom().getRoomKind().getId(), orderBookingDto.getRoomKindId());
        assertEquals(orderBooking.getRoom().getRoomKind().getClassApartment().getName(), orderBookingDto.getClassApartmentName());
        assertEquals(orderBooking.getRoom().getName(), orderBookingDto.getRoomName());
        assertEquals(orderBooking.getClient().getId(), orderBookingDto.getUserId());
        assertEquals(orderBooking.getClient().getFirstName(), orderBookingDto.getUserFirstName());
        assertEquals(orderBooking.getClient().getLastName(), orderBookingDto.getUserLastName());
        assertEquals(orderBooking.getClient().getEmail(), orderBookingDto.getUserEmail());
        assertEquals(List.of(optional.getId()), orderBookingDto.getOptionalsId());

    }

    @Test
    public void givenToListOrderBookingDto_whenMapper_ThenCorrect() {
        OrderBookingDto orderBookingDto = new OrderBookingDto(
                orderBooking.getId(),
                null,
                null,
                null,
                orderBooking.getQuantityPersons(),
                orderBooking.getRoom().getRoomKind().getRoomType().getId(),
                orderBooking.getRoom().getRoomKind().getClassApartment().getId(),
                List.of(optional.getId()),
                orderBooking.getClient().getId(),
                orderBooking.getRoom().getRoomKind().getId(),
                orderBooking.getRoom().getRoomKind().getRoomType().getName(),
                orderBooking.getRoom().getRoomKind().getClassApartment().getName(),
                orderBooking.getOrderStatus().getName(),
                orderBooking.getRoom().getName(),
                orderBooking.getSumTotal(),
                orderBooking.getClient().getFirstName(),
                orderBooking.getClient().getLastName(),
                orderBooking.getClient().getEmail()
        );

        assertEquals(List.of(orderBookingDto), orderBookingMapper.toListOrderBookingDto(List.of(orderBooking)));
    }
}
