package com.hotel_server.service.impl;

import com.hotel_database.model.repository.RoomTypeRepository;
import com.hotel_domain.model.entity.RoomType;
import com.hotel_dto.dto.RoomTypeDto;
import com.hotel_server.exceptionHandler.exception.ServerEntityNotFoundException;
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
public class RoomTypeServiceImpl implements RoomTypeService {
    private RoomTypeRepository roomTypeRepository;

    @Override
    public List<RoomType> getAll() {
        log.info("RoomType getAll");
        return roomTypeRepository.findAll();
    }

    @Override
    public RoomType getRoomTypeById(UUID id) {
        log.info("Get roomType by id " + id);
        return roomTypeRepository.findById(id).orElseThrow(() -> new ServerEntityNotFoundException(id));
    }

    @Override
    public RoomType getRoomTypeByName(String name) {
        RoomType roomType = roomTypeRepository.findRoomTypeByName(name);
        if (roomType != null) {
            log.info("Room type getRoomTypeByName is not null (name): " + name);
            return roomType;
        } else {
            log.info("Room type getRoomTypeByName is null (name): " + name);
            return null;
        }
    }

    @Override
    @Transactional
    public RoomType saveRoomType(RoomTypeDto roomTypeDTO) {
        var roomType = RoomType.builder()
                .name(roomTypeDTO.getName())
                .quantityPlaces(roomTypeDTO.getQuantityPlaces())
                .build();
        log.info("New RoomType build (name, places): " + roomTypeDTO.getName() + ", " + roomTypeDTO.getQuantityPlaces());
        return roomTypeRepository.saveAndFlush(roomType);
    }

    @Override
    @Transactional
    public RoomType updateRoomType(RoomType roomType) {
        log.info("RoomType update (id): " + roomType.getId());
        return roomTypeRepository.saveAndFlush(roomType);
    }

    @Override
    public List<RoomType> getListUniqueRoomTypesFromRooms() {
        log.info("Get list unique room types from rooms");
        return roomTypeRepository.findListUniqueRoomTypeFromRooms();
    }
}
