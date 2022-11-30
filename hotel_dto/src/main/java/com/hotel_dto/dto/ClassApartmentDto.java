package com.hotel_dto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassApartmentDto {
    private UUID id;
    private String name;
    private Double placePrice;
}
