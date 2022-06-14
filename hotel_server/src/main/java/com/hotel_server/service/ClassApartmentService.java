package com.hotel_server.service;

import com.hotel_domain.model.entity.ClassApartment;
import com.hotel_dto.dto.ClassApartmentDTO;

import java.util.List;
import java.util.Optional;

public interface ClassApartmentService {
    List<ClassApartment> getAllClassApartments();

    ClassApartment getClassApartmentById(Integer id);

    ClassApartment getClassApartmentByName(String name);

    List<ClassApartment> getListUniqueClassApartmentsFromRooms();

    ClassApartment saveClassApartment(ClassApartmentDTO classApartmentDTO);

    ClassApartment updateClassApartment (ClassApartment classApartment);
}
