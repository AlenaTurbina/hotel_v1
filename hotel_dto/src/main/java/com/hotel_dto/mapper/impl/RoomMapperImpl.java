package com.hotel_dto.mapper.impl;

import com.hotel_domain.model.entity.Room;
import com.hotel_dto.dto.RoomDTO;
import com.hotel_dto.mapper.RoomMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomMapperImpl implements RoomMapper {

    @Override
    public RoomDTO toRoomDTO(Room room) {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setName(room.getName());
        roomDTO.setRoomKind(room.getRoomKind().getId());
        roomDTO.setRoomTypeName(room.getRoomKind().getRoomType().getName());
        roomDTO.setClassApartmentName(room.getRoomKind().getClassApartment().getName());
        return roomDTO;
    }

    @Override
    public List<RoomDTO> toListRoomDTO(List<Room> rooms) {
        List<RoomDTO> roomsDTO = new ArrayList<>();
        for (Room room : rooms) {
            roomsDTO.add(toRoomDTO(room));
        }
        return roomsDTO;
    }


}
