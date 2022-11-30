package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.*;
import com.hotel_dto.dto.OrderBookingDto;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderBookingMapper {
    @Named("clientToUserId")
    static UUID clientToUserId(User user) {
        return user.getId();
    }

    @Named("roomToClassApartmentId")
    static UUID roomToClassApartmentId(Room room) {
        return room.getRoomKind().getClassApartment().getId();
    }

    @Named("roomToRoomTypeId")
    static UUID roomToRoomTypeId(Room room) {
        return room.getRoomKind().getRoomType().getId();
    }

    @Named("roomToRoomKindId")
    static UUID roomToRoomKindId(Room room) {
        return room.getRoomKind().getId();
    }

    @Named("userToUserFirstName")
    static String userToUserFirstName(User user) {
        return user.getFirstName();
    }

    @Named("userToUserLastName")
    static String userToUserLastName(User user) {
        return user.getLastName();
    }

    @Named("userToUserEmail")
    static String userToUserEmail(User user) {
        return user.getEmail();
    }

    @Named("roomToRoomTypeName")
    static String roomToRoomTypeName(Room room) {
        return room.getRoomKind().getRoomType().getName();
    }

    @Named("roomToClassApartmentName")
    static String roomToClassApartmentName(Room room) {
        return room.getRoomKind().getClassApartment().getName();
    }

    @Named("orderStatusToOrderStatusName")
    static String orderStatusToOrderStatusName(OrderStatus orderStatus) {
        return orderStatus.getName();
    }

    @Named("roomToRoomName")
    static String roomToRoomName(Room room) {
        return room.getName();
    }

    @Named("listOptionalToListOptionalId")
    static List<UUID> listOptionalToListOptionalId(List<Optional> optionals) {
        return optionals.stream().map(optional -> optional.getId())
                .collect(Collectors.toList());
    }

    @Mappings({
            @Mapping(target = "roomTypeId", source = "room", qualifiedByName = "roomToRoomTypeId"),
            @Mapping(target = "classApartmentId", source = "room", qualifiedByName = "roomToClassApartmentId"),
            @Mapping(target = "userId", source = "client", qualifiedByName = "clientToUserId"),
            @Mapping(target = "roomKindId", source = "room", qualifiedByName = "roomToRoomKindId"),
            @Mapping(target = "optionalsId", source = "optionals", qualifiedByName = "listOptionalToListOptionalId"),
            @Mapping(target = "roomTypeName", source = "room", qualifiedByName = "roomToRoomTypeName"),
            @Mapping(target = "classApartmentName", source = "room", qualifiedByName = "roomToClassApartmentName"),
            @Mapping(target = "orderStatusName", source = "orderStatus", qualifiedByName = "orderStatusToOrderStatusName"),
            @Mapping(target = "roomName", source = "room", qualifiedByName = "roomToRoomName"),
            @Mapping(target = "userFirstName", source = "client", qualifiedByName = "userToUserFirstName"),
            @Mapping(target = "userLastName", source = "client", qualifiedByName = "userToUserLastName"),
            @Mapping(target = "userEmail", source = "client", qualifiedByName = "userToUserEmail")
    })
    OrderBookingDto toOrderBookingDto(OrderBooking orderBooking);

    List<OrderBookingDto> toListOrderBookingDto(List<OrderBooking> orderBookings);
}
