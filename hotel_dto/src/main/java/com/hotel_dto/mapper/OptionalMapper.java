package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.Optional;
import com.hotel_dto.dto.OptionalDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OptionalMapper {
    Optional toOptional(OptionalDto optionalDto);

    OptionalDto toOptionalDto(Optional optional);

    List<OptionalDto> toListOptionalDto(List<Optional> optionals);
}
