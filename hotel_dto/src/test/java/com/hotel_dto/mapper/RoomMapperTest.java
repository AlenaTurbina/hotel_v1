package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.*;
import com.hotel_dto.dto.RoomDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.ComponentScan;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ComponentScan("com.hotel_domain")
public class RoomMapperTest {
    private RoomMapper roomMapper = Mappers.getMapper(RoomMapper.class);
    private ClassApartment classApartment;
    private RoomType roomType;
    private RoomKind roomKind;
    private Room room;
    public static final String id1 = "788c07d7-4275-4101-8fc3-1d77986f2129";
    public static final String id2 = "888c07d7-4275-4101-8fc3-1d77986f2129";
    public static final String id3 = "988c07d7-4275-4101-8fc3-1d77986f2129";
    public static final String id4 = "188c07d7-4275-4101-8fc3-1d77986f2129";

    @BeforeEach
    void setUp() {
        classApartment = new ClassApartment(UUID.fromString(id1), "B", 10.0);
        roomType = new RoomType(UUID.fromString(id2), "A", 1);
        roomKind = new RoomKind(UUID.fromString(id3), 10.0, roomType, classApartment);
        List<OrderBooking> emptyList = Collections.emptyList();
        room = new Room(UUID.fromString(id4), "AAA", roomKind, emptyList);
    }

    @Test
    public void givenToRoomDto_whenMapper_ThenCorrect() {
        RoomDto roomDto = roomMapper.toRoomDTO(room);

        assertEquals(room.getId(), roomDto.getId());
        assertEquals(room.getName(), roomDto.getName());
        assertEquals(room.getRoomKind().getId(), roomDto.getRoomKindId());
        assertEquals(room.getRoomKind().getClassApartment().getName(), roomDto.getClassApartmentName());
        assertEquals(room.getRoomKind().getClassApartment().getId(), roomDto.getClassApartmentId());
        assertEquals(room.getRoomKind().getRoomType().getId(), roomDto.getRoomTypeId());
        assertEquals(room.getRoomKind().getRoomType().getName(), roomDto.getRoomTypeName());
    }

    @Test
    public void givenToListRoomDto_whenMapper_ThenCorrect() {
        RoomDto roomDto = new RoomDto(
                room.getId(),
                room.getName(),
                room.getRoomKind().getId(),
                room.getRoomKind().getRoomType().getName(),
                room.getRoomKind().getClassApartment().getName(),
                room.getRoomKind().getRoomType().getId(),
                room.getRoomKind().getClassApartment().getId()
        );

        assertEquals(List.of(roomDto), roomMapper.toListRoomDTO(List.of(room)));
    }
}
