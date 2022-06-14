package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.Optional;
import com.hotel_dto.dto.OptionalDTO;

import java.util.List;

public interface OptionalMapper {
    OptionalDTO toOptionalDTO(Optional optional);

    List<OptionalDTO> toListOptionalDTO(List<Optional> optionals);

    Optional toOptional(OptionalDTO optionalDTO);
}
