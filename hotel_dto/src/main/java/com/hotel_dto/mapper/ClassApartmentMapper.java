package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.ClassApartment;
import com.hotel_dto.dto.ClassApartmentDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClassApartmentMapper {
    ClassApartment toClassApartment(ClassApartmentDto classApartmentDto);

    ClassApartmentDto toClassApartmentDto(ClassApartment classApartment);

    List<ClassApartmentDto> toListClassApartmentDto(List<ClassApartment> classApartments);
}
