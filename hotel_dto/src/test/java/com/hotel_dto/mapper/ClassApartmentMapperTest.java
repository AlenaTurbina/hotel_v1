package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.ClassApartment;
import com.hotel_dto.dto.ClassApartmentDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ComponentScan("com.hotel_domain")
public class ClassApartmentMapperTest {
    private ClassApartmentMapper classApartmentMapper = Mappers.getMapper(ClassApartmentMapper.class);
    private ClassApartment classApartment;
    private ClassApartmentDto classApartmentDto;
    public static final String id = "788c07d7-4275-4101-8fc3-1d77986f2129";

    @BeforeEach
    void setUp() {
        classApartment = new ClassApartment(UUID.fromString(id), "A", 10.0);
        classApartmentDto = new ClassApartmentDto(UUID.fromString(id), "A", 10.0);
    }

    @Test
    public void givenToClassApartmentDto_whenMapper_ThenCorrect() {
        ClassApartmentDto classApartmentDtoTest = classApartmentMapper.toClassApartmentDto(classApartment);

        assertEquals(classApartment.getId(), classApartmentDtoTest.getId());
        assertEquals(classApartment.getName(), classApartmentDtoTest.getName());
        assertEquals(classApartment.getPlacePrice(), classApartmentDtoTest.getPlacePrice());
    }

    @Test
    public void givenToClassApartment_whenMapper_ThenCorrect() {
        ClassApartment classApartmentTest = classApartmentMapper.toClassApartment(classApartmentDto);

        assertEquals(classApartmentDto.getId(), classApartmentTest.getId());
        assertEquals(classApartmentDto.getName(), classApartmentTest.getName());
        assertEquals(classApartmentDto.getPlacePrice(), classApartmentTest.getPlacePrice());
    }

    @Test
    public void givenToListClassApartmentDto_whenMapper_ThenCorrect() {
        ClassApartmentDto classApartmentDtoTest = new ClassApartmentDto(
                classApartment.getId(),
                classApartment.getName(),
                classApartment.getPlacePrice()
        );

        assertEquals(List.of(classApartmentDtoTest), classApartmentMapper.toListClassApartmentDto(List.of(classApartment)));
    }
}
