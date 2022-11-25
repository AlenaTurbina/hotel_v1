package com.hotel_dto.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RoomTypeDTO {
    private UUID id;
    private String name;
    private Integer quantityPlaces;
}
