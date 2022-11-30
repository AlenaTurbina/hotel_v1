package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.ClassApartment;
import com.hotel_domain.model.entity.RoomKind;
import com.hotel_domain.model.entity.RoomType;
import com.hotel_dto.dto.RoomKindDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ComponentScan("com.hotel_domain")
public class RoomKindMapperTest {
    private RoomKindMapper roomKindMapper = Mappers.getMapper(RoomKindMapper.class);
    private ClassApartment classApartment;
    private RoomType roomType;
    private RoomKind roomKind;
    public static final String id1 = "788c07d7-4275-4101-8fc3-1d77986f2129";
    public static final String id2 = "888c07d7-4275-4101-8fc3-1d77986f2129";
    public static final String id3 = "988c07d7-4275-4101-8fc3-1d77986f2129";

    @BeforeEach
    void setUp() {
        classApartment = new ClassApartment(UUID.fromString(id1), "B", 10.0);
        roomType = new RoomType(UUID.fromString(id2), "A", 1);
        roomKind = new RoomKind(UUID.fromString(id3), 10.0, roomType, classApartment);
    }

    @Test
    public void givenToRoomKindDto_whenMapper_ThenCorrect() {
        RoomKindDto roomKindDto = roomKindMapper.toRoomKindDTO(roomKind);

        assertEquals(roomKind.getId(), roomKindDto.getId());
        assertEquals(roomKind.getRoomPrice(), roomKindDto.getRoomPrice());
        assertEquals(roomKind.getClassApartment().getName(), roomKindDto.getClassApartmentName());
        assertEquals(roomKind.getClassApartment().getId(), roomKindDto.getClassApartment());
        assertEquals(roomKind.getRoomType().getId(), roomKindDto.getRoomType());
        assertEquals(roomKind.getRoomType().getName(), roomKindDto.getRoomTypeName());
    }

    @Test
    public void givenToListRoomKindDto_whenMapper_ThenCorrect() {
        RoomKindDto roomKindDto = new RoomKindDto(
                roomKind.getId(),
                roomKind.getRoomType().getId(),
                roomKind.getClassApartment().getId(),
                roomKind.getRoomPrice(),
                roomKind.getClassApartment().getPlacePrice(),
                roomKind.getRoomType().getQuantityPlaces(),
                roomKind.getRoomType().getName(),
                roomKind.getClassApartment().getName()
        );

        assertEquals(List.of(roomKindDto), roomKindMapper.toListRoomKindDto(List.of(roomKind)));
    }
}
