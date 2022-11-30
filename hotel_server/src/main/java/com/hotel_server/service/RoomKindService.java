package com.hotel_server.service;

import com.hotel_domain.model.entity.RoomKind;
import com.hotel_dto.dto.RoomKindDto;

import java.util.List;
import java.util.UUID;

public interface RoomKindService {
    List<RoomKind> getAllRoomKind();

    RoomKind getRoomKindById(UUID id);

    List<RoomKind> getListUniqueRoomKindsFromRooms();

    UUID getRoomKindIdByRoomTypeIdAndClassApartmentId(UUID roomTypeID, UUID classApartmentID);

    RoomKind saveRoomKind(RoomKindDto roomKindDTO);

    RoomKind updateRoomKind(RoomKindDto roomKindDTO);

    UUID testRK(UUID classApartment, UUID roomType);

    UUID testRK2(UUID classApartment);



}
