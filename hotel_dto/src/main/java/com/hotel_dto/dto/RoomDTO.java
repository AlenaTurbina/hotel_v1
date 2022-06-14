package com.hotel_dto.dto;

import lombok.Data;

@Data
public class RoomDTO {
    private Integer id;
    private String name;
    private Integer roomKind;

    private String roomTypeName;
    private String classApartmentName;

    private Integer roomType;
    private Integer classApartment;


}
