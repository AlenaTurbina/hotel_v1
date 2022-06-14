package com.hotel_dto.mapper.impl;

import com.hotel_domain.model.entity.RoomType;
import com.hotel_dto.dto.RoomTypeDTO;
import com.hotel_dto.mapper.RoomTypeMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RoomTypeMapperImpl implements RoomTypeMapper {

    @Override
    public RoomTypeDTO toRoomTypeDTO(RoomType roomType) {
        RoomTypeDTO roomTypeDTO = new RoomTypeDTO();
        roomTypeDTO.setId(roomType.getId());
        roomTypeDTO.setName(roomType.getName());
        roomTypeDTO.setQuantityPlaces(roomType.getQuantityPlaces());
        return roomTypeDTO;
    }

    @Override
    public List<RoomTypeDTO> toListRoomTypeDTO(List<RoomType> roomTypes) {
        List<RoomTypeDTO> roomTypesDTO = new ArrayList<>();
        for (RoomType roomType : roomTypes) {
            roomTypesDTO.add(toRoomTypeDTO(roomType));
        }
        return roomTypesDTO;
    }

    @Override
    public RoomType toRoomType(RoomTypeDTO roomTypeDTO) {
        return new RoomType(roomTypeDTO.getId(), roomTypeDTO.getName(), roomTypeDTO.getQuantityPlaces());
    }


}
