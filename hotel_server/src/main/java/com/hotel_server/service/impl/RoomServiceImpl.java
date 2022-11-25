package com.hotel_server.service.impl;

import com.hotel_database.model.repository.RoomRepository;
import com.hotel_domain.model.entity.Room;
import com.hotel_dto.dto.RoomDTO;
import com.hotel_server.exceptionHandler.exception.ServerEntityNotFoundException;
import com.hotel_server.service.RoomKindService;
import com.hotel_server.service.RoomService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class RoomServiceImpl implements RoomService {
    private RoomRepository roomRepository;
    private RoomKindService roomKindService;

    @Override
    public List<Room> getAllRooms() {
        log.info("Room getAll");
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(UUID id) {
        log.info("Get room by id " + id);
        return roomRepository.findById(id).orElseThrow(() -> new ServerEntityNotFoundException(id));
    }

    @Override
    public Room getRoomByName(String name) {
        Room room = roomRepository.findRoomByName(name);
        if (room != null) {
            log.info("Room getByName is not null (name): " + name);
            return room;
        } else {
            log.info("Room getByName is null (name): " + name);
            return null;
        }
    }

    @Override
    @Transactional
    public Room saveRoom(RoomDTO roomDTO) {
        var room = Room.builder()
                .name(roomDTO.getName())
                .roomKind(roomKindService.getRoomKindById(roomDTO.getRoomKind()))
                .build();
        log.info("New Room build (name, roomKindID): " + roomDTO.getName() + ", " + roomDTO.getRoomKind());
        return roomRepository.saveAndFlush(room);
    }

    @Override
    @Transactional
    public Room updateRoom(RoomDTO roomDTO) {
        var roomNew = roomRepository.findById(roomDTO.getId()).get();
        roomNew.setName(roomDTO.getName());
        roomNew.setRoomKind(roomKindService.getRoomKindById(roomDTO.getRoomKind()));
        log.info("Room update (id): " + roomDTO.getId());
        return roomRepository.saveAndFlush(roomNew);
    }

}
