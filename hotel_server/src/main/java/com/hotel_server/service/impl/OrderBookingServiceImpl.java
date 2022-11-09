package com.hotel_server.service.impl;

import com.hotel_database.model.repository.OrderBookingRepository;
import com.hotel_database.model.repository.RoomKindRepository;
import com.hotel_database.model.repository.RoomRepository;
import com.hotel_domain.model.entity.Optional;
import com.hotel_domain.model.entity.OrderBooking;
import com.hotel_domain.model.entity.Room;
import com.hotel_dto.dto.OrderBookingDTO;
import com.hotel_dto.dto.RoomKindDTO;
import com.hotel_dto.dto.UserDTO;
import com.hotel_server.exceptionHandler.exception.ServerEntityNotFoundException;
import com.hotel_server.message.Messages;
import com.hotel_server.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hotel_commons.convert.ObjectConverter.convertDataObjectBigIntIntoInteger;


@Service
@Slf4j
@AllArgsConstructor
public class OrderBookingServiceImpl implements OrderBookingService {
    private OrderBookingRepository orderBookingRepository;
    private RoomRepository roomRepository;
    private RoomKindRepository roomKindRepository;
    private RoomService roomService;
    private OptionalService optionalService;
    private UserService userService;
    private OrderStatusService orderStatusService;

    private static final Integer ID_DEFAULT_ORDER_STATUS_WAIT =
            Messages.getIntegerMessage("server.booking.idDefaultOrderStatusWait");
    private static final Integer ID_DEFAULT_ORDER_STATUS_CANCEL =
            Messages.getIntegerMessage("server.booking.idDefaultOrderStatusCancel");

    @Override
    public List<OrderBooking> getAllOrderBooking() {
        log.info("Order booking getAll");
        return orderBookingRepository.findAll();
    }

    @Override
    public OrderBooking getOrderBookingById(Integer id) {
        log.info("Get orderBooking by id " + id);
        return orderBookingRepository.findById(id).orElseThrow(() -> new ServerEntityNotFoundException(id));
    }

    @Override
    public OrderBooking saveOrderBooking(OrderBookingDTO orderBookingDTO) {
        var room = getFirstRelevantFreeRoom(orderBookingDTO);
        if (room != null) {
            log.info("room != null");
            var sum = calculationSumTotal(orderBookingDTO, room);
            var optionals = optionalService.getListOptionalById(orderBookingDTO.getOptionals());
            var client = userService.getUserByEmail(orderBookingDTO.getUserEmail());
            var orderBooking = OrderBooking.builder()
                    .date(LocalDate.now())
                    .dateArrival(orderBookingDTO.getDateArrival())
                    .dateDeparture(orderBookingDTO.getDateDeparture())
                    .quantityPersons(orderBookingDTO.getQuantityPersons())
                    .client(client)
                    .room(room)
                    .sumTotal(sum)
                    .orderStatus(orderStatusService.getOrderStatusById(ID_DEFAULT_ORDER_STATUS_WAIT))
                    .optionals(optionals)
                    .build();
            orderBookingRepository.saveAndFlush(orderBooking);
            log.info("Order booking was saved");
            return orderBooking;
        } else {
            log.info("room == null");
            return null;
        }
    }

    @Override
    public OrderBooking updateOrderBooking(OrderBookingDTO orderBookingDTO) {
        OrderBooking orderBooking = orderBookingRepository.getById(orderBookingDTO.getId());
        orderBooking.setRoom(roomService.getRoomByName(orderBookingDTO.getRoomName()));
        orderBooking.setOrderStatus(orderStatusService.getOrderStatusByName(orderBookingDTO.getOrderStatusName()));
        orderBookingRepository.saveAndFlush(orderBooking);
        log.info("Order booking was updated (id): " + orderBookingDTO.getId());
        return orderBooking;
    }

    @Override
    public Double calculationSumTotal(OrderBookingDTO orderBookingDTO, Room room) {
        double sumOptionals = 0;
        var daysRent = Period.between(orderBookingDTO.getDateArrival(), orderBookingDTO.getDateDeparture()).getDays();
        Double sumRoom = daysRent * room.getRoomKind().getRoomPrice();
        Double sumClassApartments = daysRent * room.getRoomKind().getClassApartment().getPlacePrice() * orderBookingDTO.getQuantityPersons();
        var optionals = optionalService.getListOptionalById(orderBookingDTO.getOptionals());
        if (!optionals.isEmpty()) {
            for (Optional optional : optionals) {
                sumOptionals += daysRent * optional.getOptionalPrice();
            }
        }
        Double sumTotal = sumRoom + sumClassApartments + sumOptionals;
        log.info("Calculation: (dateArrival, dateDeparture, roomPrice, classApartmentPrice) " + orderBookingDTO.getDateArrival() + ", " +
                orderBookingDTO.getDateDeparture() + ", " + room.getRoomKind().getRoomPrice() + ", " +
                room.getRoomKind().getClassApartment().getPlacePrice());
        log.info("Calculation: sumTotal: " + sumTotal);
        return sumTotal;
    }

    //Free room of a given type for the desired dates /for booking/
    @Override
    public Room getFirstRelevantFreeRoom(OrderBookingDTO orderBookingDTO) {
        LocalDate dateArrival = orderBookingDTO.getDateArrival();
        LocalDate dateDeparture = orderBookingDTO.getDateDeparture();
        Integer roomTypeId = orderBookingDTO.getRoomType();
        Integer classApartmentId = orderBookingDTO.getClassApartment();

        var resultRooms = roomRepository.findListFreeRoomsForBooking(ID_DEFAULT_ORDER_STATUS_CANCEL,
                dateArrival, dateDeparture, roomTypeId, classApartmentId);

        if (resultRooms.size() != 0) {
            log.info("Getting relevant free room on (dateArrival, dateDeparture, roomTypeID, classApartmentID): "
                    + dateArrival + ", " + dateDeparture + ", " + roomTypeId + ", " + classApartmentId);
            log.info("First relevant free room (id): " + resultRooms.get(0).getId());
            return resultRooms.get(0);
        } else
            log.info("Getting relevant free room on (dateArrival, dateDeparture, roomTypeID, classApartmentID): "
                    + dateArrival + ", " + dateDeparture + ", " + roomTypeId + ", " + classApartmentId);
        log.info("First relevant free room: null");
        return null;
    }

    //List of free rooms of a given type for the desired dates for orderBooking Update
    @Override
    public List<Room> getListFreeRoomsOnOrderBookingDates(OrderBooking orderBooking) {
        LocalDate dateArrival = orderBooking.getDateArrival();
        LocalDate dateDeparture = orderBooking.getDateDeparture();

        var resultRooms = roomRepository.findFreeRoomsOnSelectedDates(ID_DEFAULT_ORDER_STATUS_CANCEL,
                dateArrival, dateDeparture);

        //add room of current orderBooking for option
        if (orderBooking.getOrderStatus().getId() == ID_DEFAULT_ORDER_STATUS_CANCEL) {
            log.info("OrderBookingStatus == CANCEL, current room wasn't added");
        } else {
            resultRooms.add(orderBooking.getRoom());
            log.info("OrderBookingStatus != CANCEL, current room was added");
        }

        if (resultRooms.size() != 0) {
            log.info("resultRooms.size() != 0, return room");
            return resultRooms;
        } else {
            log.info("resultRooms.size() == 0, return null");
            return null;
        }
    }

    //Quantity of free rooms withDTO grouping by kind for the desired dates
    @Override
    public Map<RoomKindDTO, Long> getRoomKindsWithFreeRooms(OrderBookingDTO orderBookingDTO) {
        LocalDate dateArrival = orderBookingDTO.getDateArrival();
        LocalDate dateDeparture = orderBookingDTO.getDateDeparture();

        ArrayList<Object[]> resultRooms = roomKindRepository.findQuantityFreeRoomsWithRoomKinds(
                ID_DEFAULT_ORDER_STATUS_CANCEL, dateArrival, dateDeparture);

        Map<RoomKindDTO, Long> resultMap = new HashMap<>();
        for (Object[] resultRoom : resultRooms) {
            RoomKindDTO roomKindDTO = new RoomKindDTO();
            roomKindDTO.setId(convertDataObjectBigIntIntoInteger(resultRoom[0]));
            roomKindDTO.setRoomType(convertDataObjectBigIntIntoInteger(resultRoom[1]));
//            roomKindDTO.setClassApartment(convertDataObjectBigIntIntoInteger(resultRoom[2]));
            roomKindDTO.setRoomPrice((Double) resultRoom[3]);
            resultMap.put(roomKindDTO, convertDataObjectBigIntIntoInteger(resultRoom[4]).longValue());
        }
        return resultMap;
    }

    @Override
    public List<OrderBooking> findAllOrderBookingsByUser(UserDTO userDTO) {
        var user = userService.getUserByEmail(userDTO.getEmail());
        log.info("Order booking getAllByClient");
        var orderBookings = orderBookingRepository.findAllByClient(user);
        return orderBookings;
    }
}
