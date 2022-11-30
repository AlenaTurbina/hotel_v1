package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.RoomType;
import com.hotel_dto.dto.RoomTypeDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ComponentScan("com.hotel_domain")
public class RoomTypeMapperTest {
    private RoomTypeMapper roomTypeMapper = Mappers.getMapper(RoomTypeMapper.class);
    private RoomType roomType;
    private RoomTypeDto roomTypeDto;
    public static final String id = "788c07d7-4275-4101-8fc3-1d77986f2129";

    @BeforeEach
    void setUp() {
        roomType = new RoomType(UUID.fromString(id), "A", 1);
        roomTypeDto = new RoomTypeDto(UUID.fromString(id), "A", 1);
    }

    @Test
    public void givenToRoomTypeDto_whenMapper_ThenCorrect() {
        RoomTypeDto roomTypeDtoTest = roomTypeMapper.toRoomTypeDto(roomType);

        assertEquals(roomType.getId(), roomTypeDtoTest.getId());
        assertEquals(roomType.getName(), roomTypeDtoTest.getName());
        assertEquals(roomType.getQuantityPlaces(), roomTypeDtoTest.getQuantityPlaces());
    }

    @Test
    public void givenToRoomType_whenMapper_ThenCorrect() {
        RoomType roomTypeTest = roomTypeMapper.toRoomType(roomTypeDto);

        assertEquals(roomTypeDto.getId(), roomTypeTest.getId());
        assertEquals(roomTypeDto.getName(), roomTypeTest.getName());
        assertEquals(roomTypeDto.getQuantityPlaces(), roomTypeTest.getQuantityPlaces());
    }

    @Test
    public void givenToListRoomTypeDto_whenMapper_ThenCorrect() {
        RoomTypeDto roomTypeDtoTest = new RoomTypeDto(
                roomType.getId(),
                roomType.getName(),
                roomType.getQuantityPlaces()
        );

        assertEquals(List.of(roomTypeDtoTest), roomTypeMapper.toListRoomTypeDto(List.of(roomType)));
    }
}
