package com.hotel_dto.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RoomDTO {
    private UUID id;
    private String name;
    private UUID roomKind;

    private String roomTypeName;
    private String classApartmentName;

    private UUID roomType;
    private UUID classApartment;


}
