package com.hotel_server.service;

import com.hotel_domain.model.entity.Optional;
import com.hotel_dto.dto.OptionalDTO;

import java.util.List;

public interface OptionalService {
    List<Optional> getAllOptionals();

    Optional getOptionalById(Integer id);

    Optional getOptionalByName(String name);

    List<Optional> getListOptionalById(List<Integer> ids);

    Optional saveOptional(OptionalDTO optionalDTO);

    Optional updateOptional(Optional optional);

}
