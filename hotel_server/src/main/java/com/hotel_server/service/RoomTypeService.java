package com.hotel_server.service;

import com.hotel_domain.model.entity.RoomType;
import com.hotel_dto.dto.RoomTypeDTO;

import java.util.List;

public interface RoomTypeService {
    List<RoomType> getAll();

    RoomType getRoomTypeById(Integer id);

    RoomType getRoomTypeByName(String name);

    RoomType updateRoomType(RoomType roomType);

    RoomType saveRoomType(RoomTypeDTO roomTypeDTO);

    List<RoomType> getListUniqueRoomTypesFromRooms();
}
