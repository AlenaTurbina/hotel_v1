package com.hotel_server.service.impl;

import com.hotel_database.model.repository.ClassApartmentRepository;
import com.hotel_domain.model.entity.ClassApartment;
import com.hotel_dto.dto.ClassApartmentDto;
import com.hotel_server.exceptionHandler.exception.ServerEntityNotFoundException;
import com.hotel_server.service.ClassApartmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class ClassApartmentServiceImpl implements ClassApartmentService {
    private ClassApartmentRepository classApartmentRepository;

    @Override
    public List<ClassApartment> getAllClassApartments() {
        log.info("Class apartment getAll");
        return classApartmentRepository.findAll();
    }

    @Override
    public ClassApartment getClassApartmentById(UUID id) {
        log.info("Get class Apartment by id " + id);
        return classApartmentRepository.findById(id).orElseThrow(() -> new ServerEntityNotFoundException(id));
    }

    @Override
    public ClassApartment getClassApartmentByName(String name) {
        var classApartment = classApartmentRepository.findClassApartmentByName(name);
        if (classApartment != null) {
            log.info("Class apartment getByName is not null (name): " + name);
            return classApartment;
        } else {
            log.info("Class apartment getByName is null (name): " + name);
            return null;
        }
    }

    @Override
    public List<ClassApartment> getListUniqueClassApartmentsFromRooms() {
        log.info("Get list unique class apartments from rooms");
        return classApartmentRepository.findListUniqueClassApartmentFromRooms();
    }

    @Override
    @Transactional
    public ClassApartment saveClassApartment(ClassApartmentDto classApartmentDTO) {
        var classApartment = ClassApartment.builder()
                .name(classApartmentDTO.getName())
                .placePrice(classApartmentDTO.getPlacePrice())
                .build();
        log.info("New ClassApartment build (name, price): " + classApartmentDTO.getName() + " , " + classApartmentDTO.getPlacePrice());
        return classApartmentRepository.saveAndFlush(classApartment);
    }

    @Override
    @Transactional
    public ClassApartment updateClassApartment(ClassApartment classApartment) {
        log.info("Class apartment update (id): " + classApartment.getId());
        return  classApartmentRepository.saveAndFlush(classApartment);
    }
}
