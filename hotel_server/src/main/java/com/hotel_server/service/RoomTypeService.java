package com.hotel_server.service;

import com.hotel_domain.model.entity.RoomType;
import com.hotel_dto.dto.RoomTypeDTO;

import java.util.List;
import java.util.UUID;

public interface RoomTypeService {
    List<RoomType> getAll();

    RoomType getRoomTypeById(UUID id);

    RoomType getRoomTypeByName(String name);

    RoomType updateRoomType(RoomType roomType);

    RoomType saveRoomType(RoomTypeDTO roomTypeDTO);

    List<RoomType> getListUniqueRoomTypesFromRooms();
}
