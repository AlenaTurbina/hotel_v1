package com.hotel_server.service.impl;

import com.hotel_database.model.repository.RoomRepository;
import com.hotel_domain.model.entity.Optional;
import com.hotel_server.exceptionHandler.exception.ServerEntityNotFoundException;
import com.hotel_server.message.Messages;
import com.hotel_domain.model.entity.*;
import com.hotel_database.model.repository.OrderBookingRepository;
import com.hotel_server.service.OptionalService;
import com.hotel_server.service.RoomService;
import com.hotel_dto.dto.OrderBookingDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class OrderBookingServiceImplTest {
    @Mock
    private OrderBookingRepository orderBookingRepository;
    @Mock
    private RoomService roomService;
    @Mock
    private OptionalService optionalService;
    @Mock
    private RoomRepository roomRepository;
    @InjectMocks
    private OrderBookingServiceImpl orderBookingService;

    private OrderBooking orderBooking;
    private RoomType roomType;
    private ClassApartment classApartment;
    private RoomKind roomKind;
    private Room room;
    private OrderStatus orderStatusPaid;
    private OrderStatus orderStatusCancel;

    private static final UUID ID_DEFAULT_ORDER_STATUS_WAIT =
            Messages.getUUIDMessage("server.booking.idDefaultOrderStatusWait");
    private static final UUID ID_DEFAULT_ORDER_STATUS_CANCEL =
            Messages.getUUIDMessage("server.booking.idDefaultOrderStatusCancel");
    private static final UUID ID_DEFAULT_ORDER_STATUS_PAID =
            Messages.getUUIDMessage("server.booking.idDefaultOrderStatusPaid");

    @BeforeEach
    public void setUp() {
        classApartment = ClassApartment.builder()
                .id(UUID.randomUUID())
                .name("CA1")
                .placePrice(10.0)
                .build();

        roomType = RoomType.builder()
                .id(UUID.randomUUID())
                .name("RT1")
                .quantityPlaces(1)
                .build();

        roomKind = RoomKind.builder()
                .id(UUID.randomUUID())
                .roomType(roomType)
                .classApartment(classApartment)
                .roomPrice(10.0)
                .build();

        room = Room.builder()
                .id(UUID.randomUUID())
                .name("Room1")
                .roomKind(roomKind)
                .build();

        orderStatusPaid = OrderStatus.builder()
                .id(ID_DEFAULT_ORDER_STATUS_PAID)
                .name("OS1")
                .build();

        orderStatusCancel = OrderStatus.builder()
                .id(ID_DEFAULT_ORDER_STATUS_CANCEL)
                .name("OS2")
                .build();

        orderBooking = OrderBooking.builder()
                .id(UUID.randomUUID())
                .dateArrival(LocalDate.of(2022, 01, 05))
                .dateDeparture(LocalDate.of(2022, 01, 10))
                .room(room)
                .orderStatus(orderStatusPaid)
                .build();

    }

    @DisplayName("JUnit test for getAllOrderBooking method")
    @Test
    void test_WhenGetAllOrderBooking_ThenReturnOrderBookingList() {
        OrderBooking orderBooking1 = OrderBooking.builder()
                .id(UUID.randomUUID())
                .dateArrival(LocalDate.of(2022, 01, 05))
                .dateDeparture(LocalDate.of(2022, 01, 10))
                .build();

        given(orderBookingRepository.findAll()).willReturn(List.of(orderBooking, orderBooking1));
        List<OrderBooking> orderBookingList = orderBookingService.getAllOrderBooking();

        assertThat(orderBookingList).isNotEmpty();
        assertThat(orderBookingList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getAllOrderBooking method (empty list)")
    @Test
    void test_WhenGetAllOrderBooking_ThenReturnEmptyOrderBookingList() {
        OrderBooking orderBooking1 = OrderBooking.builder()
                .id(UUID.randomUUID())
                .build();
        given(orderBookingRepository.findAll()).willReturn(Collections.emptyList());
        List<OrderBooking> orderBookingList = orderBookingService.getAllOrderBooking();

        assertThat(orderBookingList).isEmpty();
        assertThat(orderBookingList.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getOrderBookingById method")
    @Test
    void test_GivenOrderBookingId_WhenGetOrderBookingById_thenReturnOrderBooking() {
        given(orderBookingRepository.findById(orderBooking.getId()))
                .willReturn(java.util.Optional.of(orderBooking));
        OrderBooking roomTypeExpected = orderBookingService.getOrderBookingById(orderBooking.getId());

        assertThat(roomTypeExpected).isNotNull();
    }

    @DisplayName("JUnit test for getOrderBookingById method (throw exception)")
    @Test
    void test_GivenOrderBookingId_WhenGetOrderBookingById_ThenThrowException() {
        given(orderBookingRepository.findById(orderBooking.getId()))
                .willReturn(java.util.Optional.empty());

        Assertions.assertThrows(ServerEntityNotFoundException.class,
                () -> orderBookingService.getOrderBookingById(orderBooking.getId()));
    }

    @Test
    void testCalculationSumTotalWithOptionalsNotNull() {
        Optional optional1 = new Optional(UUID.randomUUID(), null, 5.0);
        Optional optional2 = new Optional(UUID.randomUUID(), null, 10.0);
        List<Optional> optionals = new ArrayList<>(List.of(optional1, optional2));
        List<UUID> optionalsID = new ArrayList<>(List.of(optional1.getId(), optional2.getId()));
        Mockito.when(optionalService.getListOptionalById(any())).thenReturn(optionals);

        OrderBookingDto orderBookingDto1 = new OrderBookingDto();
        orderBookingDto1.setDateArrival(LocalDate.of(2022, 01, 05));
        orderBookingDto1.setDateDeparture(LocalDate.of(2022, 01, 10));
        orderBookingDto1.setQuantityPersons(2);
        orderBookingDto1.setOptionalsId(optionalsID);

        int daysRent = 10 - 5;
        int person = 2;
        double roomPrice = room.getRoomKind().getRoomPrice();
        double placePrice = room.getRoomKind().getClassApartment().getPlacePrice();
        double optionalPrice1 = optional1.getOptionalPrice();
        double optionalPrice2 = optional2.getOptionalPrice();
        Double sumRoom = daysRent * roomPrice;
        Double sumClassApartments = daysRent * person * placePrice;
        Double sumOptionals = daysRent * optionalPrice1 + daysRent * optionalPrice2;
        Double sumTotalExpected = sumRoom + sumClassApartments + sumOptionals;
        Double sumTotalActual = orderBookingService.calculationSumTotal(orderBookingDto1, room);

        assertEquals(sumTotalExpected, sumTotalActual, 0.000001);
    }

    @Test
    void testCalculationSumTotalWithOptionalsNull() {
        OrderBookingDto orderBookingDto1 = new OrderBookingDto();
        orderBookingDto1.setDateArrival(LocalDate.of(2022, 01, 05));
        orderBookingDto1.setDateDeparture(LocalDate.of(2022, 01, 10));
        orderBookingDto1.setQuantityPersons(2);

        int daysRent = 10 - 5;
        int person = 2;
        double roomPrice = room.getRoomKind().getRoomPrice();
        double placePrice = room.getRoomKind().getClassApartment().getPlacePrice();
        Double sumRoom = daysRent * roomPrice;
        Double sumClassApartments = daysRent * person * placePrice;
        Double sumTotalExpected = sumRoom + sumClassApartments;
        Double sumTotalActual = orderBookingService.calculationSumTotal(orderBookingDto1, room);

        assertEquals(sumTotalExpected, sumTotalActual, 0.000001);
    }

    @DisplayName("JUnit test for getFirstRelevantFreeRoom method")
    @Test
    void test_GivenOrderBookingDTO_WhenGetFirstRelevantFreeRoom_ThenReturnRoom() {
        Room room1 = Room.builder()
                .id(UUID.randomUUID())
                .name("R2")
                .roomKind(roomKind)
                .build();
        OrderBookingDto orderBookingDTO = new OrderBookingDto();
        orderBookingDTO.setDateArrival(orderBooking.getDateArrival());
        orderBookingDTO.setDateDeparture(orderBooking.getDateDeparture());
        orderBookingDTO.setClassApartmentId(classApartment.getId());
        orderBookingDTO.setRoomTypeId(roomType.getId());

        given(roomRepository.findListFreeRoomsForBooking(ID_DEFAULT_ORDER_STATUS_CANCEL,
                orderBooking.getDateArrival(), orderBooking.getDateDeparture(),
                roomType.getId(), classApartment.getId())).willReturn(List.of(room, room1));
        Room roomExpected = orderBookingService.getFirstRelevantFreeRoom(orderBookingDTO);

        assertThat(roomExpected).isNotNull();
        assertThat(roomExpected).isEqualTo(room);
    }

    @DisplayName("JUnit test for getFirstRelevantFreeRoom method no room")
    @Test
    void test_GivenOrderBookingDTO_WhenGetFirstRelevantFreeRoom_ThenReturnNull() {
        Room room1 = Room.builder()
                .id(UUID.randomUUID())
                .name("R2")
                .roomKind(roomKind)
                .build();
        OrderBookingDto orderBookingDTO = new OrderBookingDto();
        orderBookingDTO.setDateArrival(orderBooking.getDateArrival());
        orderBookingDTO.setDateDeparture(orderBooking.getDateDeparture());
        orderBookingDTO.setClassApartmentId(classApartment.getId());
        orderBookingDTO.setRoomTypeId(roomType.getId());

        given(roomRepository.findListFreeRoomsForBooking(ID_DEFAULT_ORDER_STATUS_CANCEL,
                orderBooking.getDateArrival(), orderBooking.getDateDeparture(),
                roomType.getId(), classApartment.getId())).willReturn(Collections.emptyList());
        Room roomExpected = orderBookingService.getFirstRelevantFreeRoom(orderBookingDTO);

        assertThat(roomExpected).isNull();
    }

    @DisplayName("JUnit test for getListFreeRoomsOnOrderBookingDates method with orderStatusCancel")
    @Test
    void test_GivenOrderBooking_WhenGetListFreeRoomsOnOrderBookingDates_ThenReturnListRoom() {
        Room room1 = Room.builder()
                .id(UUID.randomUUID())
                .name("R2")
                .roomKind(roomKind)
                .build();
        Room room2 = Room.builder()
                .id(UUID.randomUUID())
                .name("R3")
                .roomKind(roomKind)
                .build();
        orderBooking.setOrderStatus(orderStatusCancel);
        System.out.println(orderBooking.getOrderStatus().getId());
        List<Room> rooms = new ArrayList<>(List.of(room1, room2));
        given(roomRepository.findFreeRoomsOnSelectedDates(ID_DEFAULT_ORDER_STATUS_CANCEL,
                orderBooking.getDateArrival(), orderBooking.getDateDeparture())).willReturn(rooms);
        List<Room> roomListExpected = orderBookingService.getListFreeRoomsOnOrderBookingDates(orderBooking);

        assertThat(roomListExpected).isNotEmpty();
        assertThat(roomListExpected.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getListFreeRoomsOnOrderBookingDates method with orderStatusPaid")
    @Test
    void test_GivenOrderBooking_WhenGetListFreeRoomsOnOrderBookingDates_ThenReturnListRoomWithOrderRoom() {
        Room room1 = Room.builder()
                .id(UUID.randomUUID())
                .name("R2")
                .roomKind(roomKind)
                .build();
        Room room2 = Room.builder()
                .id(UUID.randomUUID())
                .name("R3")
                .roomKind(roomKind)
                .build();
        List<Room> rooms = new ArrayList<>(List.of(room1, room2));
        given(roomRepository.findFreeRoomsOnSelectedDates(ID_DEFAULT_ORDER_STATUS_CANCEL,
                orderBooking.getDateArrival(), orderBooking.getDateDeparture())).willReturn(rooms);
        List<Room> roomListExpected = orderBookingService.getListFreeRoomsOnOrderBookingDates(orderBooking);

        assertThat(roomListExpected).isNotEmpty();
        assertThat(roomListExpected.size()).isEqualTo(3);
    }

    @DisplayName("JUnit test for getListFreeRoomsOnOrderBookingDates method with orderStatusCancel and Empty list")
    @Test
    void test_GivenOrderBooking_WhenGetListFreeRoomsOnOrderBookingDates_ThenReturnEmptyList() {
        orderBooking.setOrderStatus(orderStatusCancel);
        List<Room> rooms = new ArrayList<>();
        given(roomRepository.findFreeRoomsOnSelectedDates(ID_DEFAULT_ORDER_STATUS_CANCEL,
                orderBooking.getDateArrival(), orderBooking.getDateDeparture())).willReturn(rooms);
        List<Room> roomListExpected = orderBookingService.getListFreeRoomsOnOrderBookingDates(orderBooking);

        assertThat(roomListExpected).isNull();
    }

    @DisplayName("JUnit test for getListFreeRoomsOnOrderBookingDates method with orderStatusPaid and Empty list")
    @Test
    void test_GivenOrderBooking_WhenGetListFreeRoomsOnOrderBookingDates_ThenReturnOrderRoom() {
        List<Room> rooms = new ArrayList<>();
        given(roomRepository.findFreeRoomsOnSelectedDates(ID_DEFAULT_ORDER_STATUS_CANCEL,
                orderBooking.getDateArrival(), orderBooking.getDateDeparture())).willReturn(rooms);
        List<Room> roomListExpected = orderBookingService.getListFreeRoomsOnOrderBookingDates(orderBooking);

        assertThat(roomListExpected).isNotEmpty();
        assertThat(roomListExpected.size()).isEqualTo(1);
    }

}