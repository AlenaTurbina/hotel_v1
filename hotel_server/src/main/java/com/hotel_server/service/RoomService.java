package com.hotel_server.service;


import com.hotel_domain.model.entity.Room;
import com.hotel_dto.dto.RoomDto;

import java.util.List;
import java.util.UUID;

public interface RoomService {
    List<Room> getAllRooms();

    Room getRoomById(UUID id);

    Room getRoomByName(String name);

    Room saveRoom(RoomDto roomDTO);

    Room updateRoom(RoomDto roomDTO);

}
