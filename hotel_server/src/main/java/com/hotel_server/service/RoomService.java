package com.hotel_server.service;


import com.hotel_domain.model.entity.Room;
import com.hotel_dto.dto.RoomDTO;

import java.util.List;

public interface RoomService {
    List<Room> getAllRooms();

    Room getRoomById(Integer id);

    Room getRoomByName(String name);

    Room saveRoom(RoomDTO roomDTO);

    Room updateRoom(RoomDTO roomDTO);

}
