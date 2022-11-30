package com.hotel_dto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomKindDto {
    private UUID id;
    private UUID roomType;
    private UUID classApartment;
    private Double roomPrice;
    private Double placePrice;
    private Integer quantityPlaces;
    private String roomTypeName;
    private String classApartmentName;
}
