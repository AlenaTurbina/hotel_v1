package com.hotel_server.service;

import com.hotel_domain.model.entity.RoomKind;
import com.hotel_dto.dto.RoomKindDTO;

import java.util.List;

public interface RoomKindService {
    List<RoomKind> getAllRoomKind();

    RoomKind getRoomKindById(Integer id);

    List<RoomKind> getListUniqueRoomKindsFromRooms();

    Integer getRoomKindIdByRoomTypeIdAndClassApartmentId(Integer roomTypeID, Integer classApartmentID);

    RoomKind saveRoomKind(RoomKindDTO roomKindDTO);

    RoomKind updateRoomKind(RoomKindDTO roomKindDTO);

}
