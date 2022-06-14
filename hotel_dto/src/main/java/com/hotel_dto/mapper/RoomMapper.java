package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.Room;
import com.hotel_dto.dto.RoomDTO;

import java.util.List;

public interface RoomMapper {
    RoomDTO toRoomDTO(Room room);

    List<RoomDTO> toListRoomDTO(List<Room> rooms);

}
