package com.hotel_server.service;

import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import com.hotel_domain.model.entity.RoomKind;
import com.hotel_dto.dto.RoomKindDTO;

import java.util.List;
import java.util.UUID;

public interface RoomKindService {
    List<RoomKind> getAllRoomKind();

    RoomKind getRoomKindById(UUID id);

    List<RoomKind> getListUniqueRoomKindsFromRooms();

    UUID getRoomKindIdByRoomTypeIdAndClassApartmentId(UUID roomTypeID, UUID classApartmentID);

    RoomKind saveRoomKind(RoomKindDTO roomKindDTO);

    RoomKind updateRoomKind(RoomKindDTO roomKindDTO);

    UUID testRK(UUID classApartment, UUID roomType);

}
