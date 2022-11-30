package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.Room;
import com.hotel_domain.model.entity.RoomKind;
import com.hotel_dto.dto.RoomDto;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {
    @Named("roomKindToRoomKindId")
    static UUID roomKindToRoomKindId(RoomKind roomKind) {
        return roomKind.getId();
    }

    @Named("roomKindToRoomTypeId")
    static UUID roomKindToRoomTypeId(RoomKind roomKind) {
        return roomKind.getRoomType().getId();
    }

    @Named("roomKindToRoomTypeName")
    static String roomKindToRoomTypeName(RoomKind roomKind) {
        return roomKind.getRoomType().getName();
    }

    @Named("roomKindToClassApartmentId")
    static UUID roomKindToClassApartmentId(RoomKind roomKind) {
        return roomKind.getClassApartment().getId();
    }

    @Named("roomKindToClassApartmentName")
    static String roomKindToClassApartmentName(RoomKind roomKind) {
        return roomKind.getClassApartment().getName();
    }

    @Mappings({
            @Mapping(target = "roomKindId", source = "roomKind", qualifiedByName = "roomKindToRoomKindId"),
            @Mapping(target = "roomTypeId", source = "roomKind", qualifiedByName = "roomKindToRoomTypeId"),
            @Mapping(target = "classApartmentId", source = "roomKind", qualifiedByName = "roomKindToClassApartmentId"),
            @Mapping(target = "roomTypeName", source = "roomKind", qualifiedByName = "roomKindToRoomTypeName"),
            @Mapping(target = "classApartmentName", source = "roomKind", qualifiedByName = "roomKindToClassApartmentName")
    })
    RoomDto toRoomDTO(Room room);

    List<RoomDto> toListRoomDTO(List<Room> rooms);
}
