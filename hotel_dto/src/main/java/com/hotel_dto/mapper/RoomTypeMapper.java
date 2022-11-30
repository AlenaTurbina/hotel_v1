package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.RoomType;
import com.hotel_dto.dto.RoomTypeDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoomTypeMapper {
    RoomTypeDto toRoomTypeDto(RoomType roomType);

    RoomType toRoomType(RoomTypeDto roomTypeDto);

    List<RoomTypeDto> toListRoomTypeDto(List<RoomType> roomTypes);
}
