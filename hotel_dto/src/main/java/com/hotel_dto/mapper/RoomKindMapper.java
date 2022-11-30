package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.ClassApartment;
import com.hotel_domain.model.entity.RoomKind;
import com.hotel_domain.model.entity.RoomType;
import com.hotel_dto.dto.RoomKindDto;
import org.mapstruct.*;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomKindMapper {
    @Named("roomTypeToRoomTypeId")
    static UUID roomTypeToRoomTypeId(RoomType roomType) {
        return roomType.getId();
    }

    @Named("classApartmentToClassApartmentId")
    static UUID classApartmentToClassApartmentId(ClassApartment classApartment) {
        return classApartment.getId();
    }

    @Named("classApartmentToPlacePrice")
    static Double classApartmentToPlacePrice(ClassApartment classApartment) {
        return classApartment.getPlacePrice();
    }

    @Named("roomTypeToQuantityPlaces")
    static Integer roomTypeToQuantityPlaces(RoomType roomType) {
        return roomType.getQuantityPlaces();
    }

    @Named("classApartmentToClassApartmentName")
    static String classApartmentToClassApartmentName(ClassApartment classApartment) {
        return classApartment.getName();
    }

    @Named("roomTypeToRoomTypeName")
    static String roomTypeToRoomTypeName(RoomType roomType) {
        return roomType.getName();
    }

    @Mappings({
            @Mapping(target = "roomType", source = "roomType", qualifiedByName = "roomTypeToRoomTypeId"),
            @Mapping(target = "roomTypeName", source = "roomType", qualifiedByName = "roomTypeToRoomTypeName"),
            @Mapping(target = "quantityPlaces", source = "roomType", qualifiedByName = "roomTypeToQuantityPlaces"),
            @Mapping(target = "classApartment", source = "classApartment", qualifiedByName = "classApartmentToClassApartmentId"),
            @Mapping(target = "classApartmentName", source = "classApartment", qualifiedByName = "classApartmentToClassApartmentName"),
            @Mapping(target = "placePrice", source = "classApartment", qualifiedByName = "classApartmentToPlacePrice")
    })
    RoomKindDto toRoomKindDTO(RoomKind roomKind);

    List<RoomKindDto> toListRoomKindDto(List<RoomKind> roomKinds);
}
