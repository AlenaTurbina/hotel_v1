package com.hotel_dto.dto;

import lombok.Data;

@Data
public class RoomKindDTO {
    private Integer id;
    private Integer roomType;
    private Integer classApartment;
    private Double roomPrice;
    private Double placePrice;
    private Integer quantityPlaces;
    private String roomTypeName;
    private String classApartmentName;
}
