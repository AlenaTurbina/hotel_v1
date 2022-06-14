package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.RoomKind;
import com.hotel_dto.dto.RoomKindDTO;

import java.util.List;

public interface RoomKindMapper {
    RoomKindDTO toRoomKindDTO(RoomKind roomKind);

    List<RoomKindDTO> toListRoomKindDTO(List<RoomKind> roomKinds);

}
