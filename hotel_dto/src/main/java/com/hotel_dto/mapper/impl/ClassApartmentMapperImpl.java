package com.hotel_dto.mapper.impl;

import com.hotel_domain.model.entity.ClassApartment;
import com.hotel_dto.dto.ClassApartmentDTO;
import com.hotel_dto.mapper.ClassApartmentMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClassApartmentMapperImpl implements ClassApartmentMapper {

    @Override
    public ClassApartmentDTO toClassApartmentDTO(ClassApartment classApartment) {
        ClassApartmentDTO classApartmentDTO = new ClassApartmentDTO();
        classApartmentDTO.setId(classApartment.getId());
        classApartmentDTO.setName(classApartment.getName());
        classApartmentDTO.setPlacePrice(classApartment.getPlacePrice());
        return classApartmentDTO;
    }

    @Override
    public List<ClassApartmentDTO> toListClassApartmentDTO(List<ClassApartment> classApartments) {
        List<ClassApartmentDTO> classApartmentsDTO = new ArrayList<>();
        for (ClassApartment classApartment : classApartments) {
            classApartmentsDTO.add(toClassApartmentDTO(classApartment));
        }
        return classApartmentsDTO;
    }

    @Override
    public ClassApartment toClassApartment(ClassApartmentDTO classApartmentDTO) {
        return new ClassApartment(classApartmentDTO.getId(), classApartmentDTO.getName(), classApartmentDTO.getPlacePrice());
    }
}
