package com.hotel_server.service.impl;

import com.hotel_database.model.repository.ClassApartmentRepository;
import com.hotel_database.model.repository.RoomKindRepository;
import com.hotel_database.model.repository.RoomTypeRepository;
import com.hotel_domain.model.entity.RoomKind;
import com.hotel_dto.dto.RoomKindDto;
import com.hotel_server.exceptionHandler.exception.ServerEntityNotFoundException;
import com.hotel_server.service.ClassApartmentService;
import com.hotel_server.service.RoomKindService;
import com.hotel_server.service.RoomTypeService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class RoomKindServiceImpl implements RoomKindService {
    private RoomKindRepository roomKindRepository;
    private RoomTypeRepository roomTypeRepository;
    private ClassApartmentRepository classApartmentRepository;
    private ClassApartmentService classApartmentService;
    private RoomTypeService roomTypeService;

    @Override
    public List<RoomKind> getAllRoomKind() {
        log.info("RoomKind getAll");
        return roomKindRepository.findAll();
    }

    @Override
    public RoomKind getRoomKindById(UUID id) {
        log.info("Get roomKind by id " + id);
        return roomKindRepository.findById(id).orElseThrow(() -> new ServerEntityNotFoundException(id));
    }

    @Override
    public List<RoomKind> getListUniqueRoomKindsFromRooms() {
        log.info("Get list unique room kinds from rooms");
        return roomKindRepository.findListUniqueRoomKindFromRooms();
    }

    @Override
    public UUID getRoomKindIdByRoomTypeIdAndClassApartmentId(UUID roomTypeID, UUID classApartmentID) {
        log.info("Get room Kind by (roomTypeID, ClassApartmentID): " + roomTypeID + ", " + classApartmentID);
        return roomKindRepository.findRoomKindIDByRoomTypeAndClassApartmentID1(classApartmentID, roomTypeID);
    }

    @Override
    @Transactional
    public RoomKind saveRoomKind(RoomKindDto roomKindDTO) {
        var roomKind = new RoomKind();
        roomKind.setRoomType(roomTypeService.getRoomTypeById(roomKindDTO.getRoomType()));
        roomKind.setClassApartment(classApartmentService.getClassApartmentById(roomKindDTO.getClassApartment()));
        roomKind.setRoomPrice(roomKindDTO.getRoomPrice());
        log.info("New room kind set (roomType, classApartment, price): " + roomKindDTO.getRoomType() + ", "
                + roomKindDTO.getClassApartment() + ", " + roomKindDTO.getRoomPrice());
        return roomKindRepository.saveAndFlush(roomKind);
    }

    @Override
    @Transactional
    public RoomKind updateRoomKind(RoomKindDto roomKindDTO) {
        var roomKindNew = roomKindRepository.getById(roomKindDTO.getId());
        roomKindNew.setRoomType(roomTypeRepository.getById(roomKindDTO.getRoomType()));
        roomKindNew.setClassApartment(classApartmentRepository.getById(roomKindDTO.getClassApartment()));
        roomKindNew.setRoomPrice(roomKindDTO.getRoomPrice());
        log.info("Room kind update (id): " + roomKindDTO.getId());
        return roomKindRepository.saveAndFlush(roomKindNew);
    }


    @Override
    public UUID testRK(UUID classApartment, UUID roomType) {
        System.out.println("from");
        return roomKindRepository.findRoomKindIDByRoomTypeAndClassApartmentID1(classApartment, roomType);
    }

    @Override
    public UUID testRK2(UUID classApartment) {
        System.out.println("from");
        System.out.println(classApartment);
   //     String ca = concat("'", classApartment, "'");
    //    System.out.println(ca);
       String ca = classApartment.toString();
   //     return roomKindRepository.findRoomKindIDByRoomTypeAndClassApartmentID2(classApartment);
        return UUID.randomUUID();
    }
}
