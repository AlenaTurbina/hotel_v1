package com.hotel_dto.mapper.impl;

import com.hotel_domain.model.entity.RoomKind;
import com.hotel_dto.dto.RoomKindDTO;
import com.hotel_dto.mapper.RoomKindMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomKindMapperImpl implements RoomKindMapper {

    @Override
    public RoomKindDTO toRoomKindDTO(RoomKind roomKind) {
        RoomKindDTO roomKindDTO = new RoomKindDTO();
        roomKindDTO.setId(roomKind.getId());
        roomKindDTO.setRoomPrice(roomKind.getRoomPrice());
        roomKindDTO.setPlacePrice(roomKind.getClassApartment().getPlacePrice());
        roomKindDTO.setQuantityPlaces(roomKind.getRoomType().getQuantityPlaces());
        roomKindDTO.setRoomType(roomKind.getRoomType().getId());
        roomKindDTO.setClassApartment(roomKind.getClassApartment().getId());
        roomKindDTO.setClassApartmentName(roomKind.getClassApartment().getName());
        roomKindDTO.setRoomTypeName(roomKind.getRoomType().getName());
        return roomKindDTO;
    }

    @Override
    public List<RoomKindDTO> toListRoomKindDTO(List<RoomKind> roomKinds) {
        List<RoomKindDTO> roomKindsDTO = new ArrayList<>();
        for (RoomKind roomKind : roomKinds) {
            roomKindsDTO.add(toRoomKindDTO(roomKind));
        }
        return roomKindsDTO;
    }

}
