package com.hotel_server.service.impl;

import com.hotel_database.model.repository.RoomRepository;
import com.hotel_server.exceptionHandler.exception.ServerEntityNotFoundException;
import com.hotel_server.message.Messages;
import com.hotel_domain.model.entity.*;
import com.hotel_database.model.repository.OrderBookingRepository;
import com.hotel_server.service.OptionalService;
import com.hotel_server.service.RoomService;
import com.hotel_dto.dto.OrderBookingDTO;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    private static final Integer ID_DEFAULT_ORDER_STATUS_PAID = Messages.getIntegerMessage("server.booking.idDefaultOrderStatusPaid");
    private static final Integer ID_DEFAULT_ORDER_STATUS_CANCEL = Messages.getIntegerMessage("server.booking.idDefaultOrderStatusCancel");

//    @BeforeEach
//    public void setUp() {
//        classApartment = ClassApartment.builder()
//                .id(1)
//                .name("CA1")
//                .placePrice(10.0)
//                .build();
//
//        roomType = RoomType.builder()
//                .id(1)
//                .name("RT1")
//                .quantityPlaces(1)
//                .build();
//
//        roomKind = RoomKind.builder()
//                .id(1)
//                .roomType(roomType)
//                .classApartment(classApartment)
//                .roomPrice(10.0)
//                .build();
//
//        room = Room.builder()
//                .id(1)
//                .name("Room1")
//                .roomKind(roomKind)
//                .build();
//        orderStatusPaid = OrderStatus.builder()
//                .id(ID_DEFAULT_ORDER_STATUS_PAID)
//                .name("OS1")
//                .build();
//
//        orderStatusCancel = OrderStatus.builder()
//                .id(ID_DEFAULT_ORDER_STATUS_CANCEL)
//                .name("OS2")
//                .build();
//
//        orderBooking = OrderBooking.builder()
//                .id(1)
//                .dateArrival(LocalDate.of(2022, 01, 05))
//                .dateDeparture(LocalDate.of(2022, 01, 10))
//                .room(room)
//                .orderStatus(orderStatusPaid)
//                .build();
//
//    }
//
//    @DisplayName("JUnit test for getAllOrderBooking method")
//    @Test
//    void test_WhenGetAllOrderBooking_ThenReturnOrderBookingList() {
//        OrderBooking orderBooking1 = OrderBooking.builder()
//                .id(2)
//                .dateArrival(LocalDate.of(2022, 01, 05))
//                .dateDeparture(LocalDate.of(2022, 01, 10))
//                .build();
//
//        given(orderBookingRepository.findAll()).willReturn(List.of(orderBooking, orderBooking1));
//        List<OrderBooking> orderBookingList = orderBookingService.getAllOrderBooking();
//
//        assertThat(orderBookingList).isNotEmpty();
//        assertThat(orderBookingList.size()).isEqualTo(2);
//    }
//
//    @DisplayName("JUnit test for getAllOrderBooking method (empty list)")
//    @Test
//    void test_WhenGetAllOrderBooking_ThenReturnEmptyOrderBookingList() {
//        OrderBooking orderBooking1 = OrderBooking.builder()
//                .id(2)
//                .build();
//        given(orderBookingRepository.findAll()).willReturn(Collections.emptyList());
//        List<OrderBooking> orderBookingList = orderBookingService.getAllOrderBooking();
//
//        assertThat(orderBookingList).isEmpty();
//        assertThat(orderBookingList.size()).isEqualTo(0);
//    }
//
//    @DisplayName("JUnit test for getOrderBookingById method")
//    @Test
//    void test_GivenOrderBookingId_WhenGetOrderBookingById_thenReturnOrderBooking() {
//        given(orderBookingRepository.findById(orderBooking.getId()))
//                .willReturn(java.util.Optional.of(orderBooking));
//        OrderBooking roomTypeExpected = orderBookingService.getOrderBookingById(orderBooking.getId());
//
//        assertThat(roomTypeExpected).isNotNull();
//    }
//
//    @DisplayName("JUnit test for getOrderBookingById method (throw exception)")
//    @Test
//    void test_GivenOrderBookingId_WhenGetOrderBookingById_ThenThrowException() {
//        given(orderBookingRepository.findById(orderBooking.getId()))
//                .willReturn(java.util.Optional.empty());
//
//        Assertions.assertThrows(ServerEntityNotFoundException.class,
//                () -> orderBookingService.getOrderBookingById(orderBooking.getId()));
//    }
//
//    @Test
//    void testCalculationSumTotalWithOptionalsNotNull() {
//        Optional optional1 = new Optional(1, null, 5.0);
//        Optional optional2 = new Optional(2, null, 10.0);
//        List<Optional> optionals = new ArrayList<>(List.of(optional1, optional2));
//        List<Integer> optionalsID = new ArrayList<>(List.of(optional1.getId(), optional2.getId()));
//        Mockito.when(optionalService.getListOptionalById(any())).thenReturn(optionals);
//
//        OrderBookingDTO orderBookingDTO1 = new OrderBookingDTO();
//        orderBookingDTO1.setDateArrival(LocalDate.of(2022, 01, 05));
//        orderBookingDTO1.setDateDeparture(LocalDate.of(2022, 01, 10));
//        orderBookingDTO1.setQuantityPersons(2);
//        orderBookingDTO1.setOptionals(optionalsID);
//
//        int daysRent = 10 - 5;
//        int person = 2;
//        double roomPrice = room.getRoomKind().getRoomPrice();
//        double placePrice = room.getRoomKind().getClassApartment().getPlacePrice();
//        double optionalPrice1 = optional1.getOptionalPrice();
//        double optionalPrice2 = optional2.getOptionalPrice();
//        Double sumRoom = daysRent * roomPrice;
//        Double sumClassApartments = daysRent * person * placePrice;
//        Double sumOptionals = daysRent * optionalPrice1 + daysRent * optionalPrice2;
//        Double sumTotalExpected = sumRoom + sumClassApartments + sumOptionals;
//        Double sumTotalActual = orderBookingService.calculationSumTotal(orderBookingDTO1, room);
//
//        assertEquals(sumTotalExpected, sumTotalActual, 0.000001);
//    }
//
//    @Test
//    void testCalculationSumTotalWithOptionalsNull() {
//        OrderBookingDTO orderBookingDTO1 = new OrderBookingDTO();
//        orderBookingDTO1.setDateArrival(LocalDate.of(2022, 01, 05));
//        orderBookingDTO1.setDateDeparture(LocalDate.of(2022, 01, 10));
//        orderBookingDTO1.setQuantityPersons(2);
//
//        int daysRent = 10 - 5;
//        int person = 2;
//        double roomPrice = room.getRoomKind().getRoomPrice();
//        double placePrice = room.getRoomKind().getClassApartment().getPlacePrice();
//        Double sumRoom = daysRent * roomPrice;
//        Double sumClassApartments = daysRent * person * placePrice;
//        Double sumTotalExpected = sumRoom + sumClassApartments;
//        Double sumTotalActual = orderBookingService.calculationSumTotal(orderBookingDTO1, room);
//
//        assertEquals(sumTotalExpected, sumTotalActual, 0.000001);
//    }
//
//    @DisplayName("JUnit test for getFirstRelevantFreeRoom method")
//    @Test
//    void test_GivenOrderBookingDTO_WhenGetFirstRelevantFreeRoom_ThenReturnRoom() {
//        Room room1 = Room.builder()
//                .id(2)
//                .name("R2")
//                .roomKind(roomKind)
//                .build();
//        OrderBookingDTO orderBookingDTO = new OrderBookingDTO();
//        orderBookingDTO.setDateArrival(orderBooking.getDateArrival());
//        orderBookingDTO.setDateDeparture(orderBooking.getDateDeparture());
//        orderBookingDTO.setClassApartment(classApartment.getId());
//        orderBookingDTO.setRoomType(roomType.getId());
//
//        given(roomRepository.findListFreeRoomsForBooking(ID_DEFAULT_ORDER_STATUS_CANCEL,
//                orderBooking.getDateArrival(), orderBooking.getDateDeparture(),
//                roomType.getId(), classApartment.getId())).willReturn(List.of(room, room1));
//        Room roomExpected = orderBookingService.getFirstRelevantFreeRoom(orderBookingDTO);
//
//        assertThat(roomExpected).isNotNull();
//        assertThat(roomExpected).isEqualTo(room);
//    }
//
//    @DisplayName("JUnit test for getFirstRelevantFreeRoom method no room")
//    @Test
//    void test_GivenOrderBookingDTO_WhenGetFirstRelevantFreeRoom_ThenReturnNull() {
//        Room room1 = Room.builder()
//                .id(2)
//                .name("R2")
//                .roomKind(roomKind)
//                .build();
//        OrderBookingDTO orderBookingDTO = new OrderBookingDTO();
//        orderBookingDTO.setDateArrival(orderBooking.getDateArrival());
//        orderBookingDTO.setDateDeparture(orderBooking.getDateDeparture());
//        orderBookingDTO.setClassApartment(classApartment.getId());
//        orderBookingDTO.setRoomType(roomType.getId());
//
//        given(roomRepository.findListFreeRoomsForBooking(ID_DEFAULT_ORDER_STATUS_CANCEL,
//                orderBooking.getDateArrival(), orderBooking.getDateDeparture(),
//                roomType.getId(), classApartment.getId())).willReturn(Collections.emptyList());
//        Room roomExpected = orderBookingService.getFirstRelevantFreeRoom(orderBookingDTO);
//
//        assertThat(roomExpected).isNull();
//    }
//
//    @DisplayName("JUnit test for getListFreeRoomsOnOrderBookingDates method with orderStatusCancel")
//    @Test
//    void test_GivenOrderBooking_WhenGetListFreeRoomsOnOrderBookingDates_ThenReturnListRoom() {
//        Room room1 = Room.builder()
//                .id(2)
//                .name("R2")
//                .roomKind(roomKind)
//                .build();
//        Room room2 = Room.builder()
//                .id(3)
//                .name("R3")
//                .roomKind(roomKind)
//                .build();
//        orderBooking.setOrderStatus(orderStatusCancel);
//        List<Room> rooms = new ArrayList<>(List.of(room1, room2));
//        given(roomRepository.findFreeRoomsOnSelectedDates(ID_DEFAULT_ORDER_STATUS_CANCEL,
//                orderBooking.getDateArrival(), orderBooking.getDateDeparture())).willReturn(rooms);
//
//        List<Room> roomListExpected = orderBookingService.getListFreeRoomsOnOrderBookingDates(orderBooking);
//
//        assertThat(roomListExpected).isNotEmpty();
//        assertThat(roomListExpected.size()).isEqualTo(2);
//    }
//
//    @DisplayName("JUnit test for getListFreeRoomsOnOrderBookingDates method with orderStatusPaid")
//    @Test
//    void test_GivenOrderBooking_WhenGetListFreeRoomsOnOrderBookingDates_ThenReturnListRoomWithOrderRoom() {
//        Room room1 = Room.builder()
//                .id(2)
//                .name("R2")
//                .roomKind(roomKind)
//                .build();
//        Room room2 = Room.builder()
//                .id(3)
//                .name("R3")
//                .roomKind(roomKind)
//                .build();
//        List<Room> rooms = new ArrayList<>(List.of(room1, room2));
//        given(roomRepository.findFreeRoomsOnSelectedDates(ID_DEFAULT_ORDER_STATUS_CANCEL,
//                orderBooking.getDateArrival(), orderBooking.getDateDeparture())).willReturn(rooms);
//        List<Room> roomListExpected = orderBookingService.getListFreeRoomsOnOrderBookingDates(orderBooking);
//
//        assertThat(roomListExpected).isNotEmpty();
//        assertThat(roomListExpected.size()).isEqualTo(3);
//    }
//
//    @DisplayName("JUnit test for getListFreeRoomsOnOrderBookingDates method with orderStatusCancel and Empty list")
//    @Test
//    void test_GivenOrderBooking_WhenGetListFreeRoomsOnOrderBookingDates_ThenReturnEmptyList() {
//        orderBooking.setOrderStatus(orderStatusCancel);
//        List<Room> rooms = new ArrayList<>();
//        given(roomRepository.findFreeRoomsOnSelectedDates(ID_DEFAULT_ORDER_STATUS_CANCEL,
//                orderBooking.getDateArrival(), orderBooking.getDateDeparture())).willReturn(rooms);
//        List<Room> roomListExpected = orderBookingService.getListFreeRoomsOnOrderBookingDates(orderBooking);
//
//        assertThat(roomListExpected).isNull();
//    }
//
//    @DisplayName("JUnit test for getListFreeRoomsOnOrderBookingDates method with orderStatusPaid and Empty list")
//    @Test
//    void test_GivenOrderBooking_WhenGetListFreeRoomsOnOrderBookingDates_ThenReturnOrderRoom() {
//        List<Room> rooms = new ArrayList<>();
//        given(roomRepository.findFreeRoomsOnSelectedDates(ID_DEFAULT_ORDER_STATUS_CANCEL,
//                orderBooking.getDateArrival(), orderBooking.getDateDeparture())).willReturn(rooms);
//        List<Room> roomListExpected = orderBookingService.getListFreeRoomsOnOrderBookingDates(orderBooking);
//
//        assertThat(roomListExpected).isNotEmpty();
//        assertThat(roomListExpected.size()).isEqualTo(1);
//    }

}