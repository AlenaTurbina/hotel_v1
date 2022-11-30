package com.hotel_server.service.impl;

import com.hotel_database.model.repository.OptionalRepository;
import com.hotel_domain.model.entity.Optional;
import com.hotel_dto.dto.OptionalDto;
import com.hotel_server.exceptionHandler.exception.ServerEntityNotFoundException;
import com.hotel_server.service.OptionalService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class OptionalServiceImpl implements OptionalService {
    private OptionalRepository optionalRepository;

    @Override
    public List<Optional> getAllOptionals() {
        log.info("Optional getAll");
        return optionalRepository.findAll();
    }

    @Override
    public Optional getOptionalById(UUID id) {
        log.info("Get optional by id " + id);
        return optionalRepository.findById(id).orElseThrow(() -> new ServerEntityNotFoundException(id));
    }

    @Override
    public Optional getOptionalByName(String name) {
        var optional = optionalRepository.findOptionalByName(name);
        if (optional != null) {
            log.info("Optional getByName is not null (name): " + name);
            return optional;
        } else {
            log.info("Optional getByName is null (name): " + name);
            return null;
        }
    }

    @Override
    public List<Optional> getListOptionalById(List<UUID> ids) {
        List<Optional> optionals = new ArrayList<>();
        for (UUID id : ids) {
            optionals.add(getOptionalById(id));
            log.info("Optional getListById add optional (id): " + id);
        }
        return optionals;
    }

    @Override
    @Transactional
    public Optional saveOptional(OptionalDto optionalDTO) {
        var optional = Optional.builder()
                .name(optionalDTO.getName())
                .optionalPrice(optionalDTO.getOptionalPrice())
                .build();
        log.info("New Optional build (name, price): " + optionalDTO.getName() + ", " + optionalDTO.getOptionalPrice());
        return optionalRepository.saveAndFlush(optional);
    }

    @Override
    @Transactional
    public Optional updateOptional(Optional optional) {
        log.info("Optional update (id): " + optional.getId());
        return optionalRepository.saveAndFlush(optional);
    }

}
