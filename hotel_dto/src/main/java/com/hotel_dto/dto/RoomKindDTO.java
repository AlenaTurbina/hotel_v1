package com.hotel_dto.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import java.util.UUID;

@Data
public class RoomKindDTO {
    private UUID id;
    private UUID roomType;
    private UUID classApartment;
    private Double roomPrice;
    private Double placePrice;
    private Integer quantityPlaces;
    private String roomTypeName;
    private String classApartmentName;
}
