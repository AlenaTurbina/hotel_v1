package com.hotel_server.service;

import com.hotel_domain.model.entity.Optional;
import com.hotel_dto.dto.OptionalDTO;

import java.util.List;
import java.util.UUID;

public interface OptionalService {
    List<Optional> getAllOptionals();

    Optional getOptionalById(UUID id);

    Optional getOptionalByName(String name);

    List<Optional> getListOptionalById(List<UUID> ids);

    Optional saveOptional(OptionalDTO optionalDTO);

    Optional updateOptional(Optional optional);

}
