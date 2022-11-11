package com.hotel_dto.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class OptionalDTO {
    private UUID id;
    private String name;
    private Double optionalPrice;
}
