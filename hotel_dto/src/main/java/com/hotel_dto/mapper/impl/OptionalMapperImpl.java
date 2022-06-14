package com.hotel_dto.mapper.impl;

import com.hotel_domain.model.entity.Optional;
import com.hotel_dto.dto.OptionalDTO;
import com.hotel_dto.mapper.OptionalMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OptionalMapperImpl implements OptionalMapper {

    @Override
    public OptionalDTO toOptionalDTO(Optional optional) {
        OptionalDTO optionalDTO = new OptionalDTO();
        optionalDTO.setId(optional.getId());
        optionalDTO.setName(optional.getName());
        optionalDTO.setOptionalPrice(optional.getOptionalPrice());
        return optionalDTO;
    }

    @Override
    public List<OptionalDTO> toListOptionalDTO(List<Optional> optionals) {
        List<OptionalDTO> optionalsDTO = new ArrayList<>();
        for (Optional optional : optionals) {
            optionalsDTO.add(toOptionalDTO(optional));
        }
        return optionalsDTO;
    }

    @Override
    public Optional toOptional(OptionalDTO optionalDTO) {
        return new Optional(optionalDTO.getId(), optionalDTO.getName(), optionalDTO.getOptionalPrice());
    }
}
