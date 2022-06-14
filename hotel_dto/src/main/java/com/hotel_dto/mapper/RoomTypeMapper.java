package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.RoomType;
import com.hotel_dto.dto.RoomTypeDTO;

import java.util.List;

public interface RoomTypeMapper {
    RoomTypeDTO toRoomTypeDTO(RoomType roomType);

    List<RoomTypeDTO> toListRoomTypeDTO(List<RoomType> roomType);

    RoomType toRoomType(RoomTypeDTO roomTypeDTO);

}
