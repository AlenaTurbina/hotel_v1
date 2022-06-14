package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.ClassApartment;
import com.hotel_dto.dto.ClassApartmentDTO;

import java.util.List;

public interface ClassApartmentMapper {
    ClassApartmentDTO toClassApartmentDTO(ClassApartment classApartment);

    List<ClassApartmentDTO> toListClassApartmentDTO(List<ClassApartment> classApartments);

    ClassApartment toClassApartment(ClassApartmentDTO classApartmentDTO);
}
