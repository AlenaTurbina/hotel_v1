package com.hotel_dto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomDto {
    private UUID id;
    private String name;
    private UUID roomKindId;
    private String roomTypeName;
    private String classApartmentName;
    private UUID roomTypeId;
    private UUID classApartmentId;
}
