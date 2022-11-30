package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.Optional;
import com.hotel_dto.dto.OptionalDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ComponentScan("com.hotel_domain")
public class OptionalMapperTest {
    private OptionalMapper optionalMapper = Mappers.getMapper(OptionalMapper.class);
    private Optional optional;
    private OptionalDto optionalDto;
    public static final String id = "788c07d7-4275-4101-8fc3-1d77986f2129";

    @BeforeEach
    void setUp() {
        optional = new Optional(UUID.fromString(id), "A", 10.0);
        optionalDto = new OptionalDto(UUID.fromString(id), "A", 10.0);
    }

    @Test
    public void givenToOptionalDto_whenMapper_ThenCorrect() {
        OptionalDto optionalDtoTest = optionalMapper.toOptionalDto(optional);

        assertEquals(optional.getId(), optionalDtoTest.getId());
        assertEquals(optional.getName(), optionalDtoTest.getName());
        assertEquals(optional.getOptionalPrice(), optionalDtoTest.getOptionalPrice());
    }

    @Test
    public void givenToOptional_whenMapper_ThenCorrect() {
        Optional optionalTest = optionalMapper.toOptional(optionalDto);

        assertEquals(optionalDto.getId(), optionalTest.getId());
        assertEquals(optionalDto.getName(), optionalTest.getName());
        assertEquals(optionalDto.getOptionalPrice(), optionalTest.getOptionalPrice());
    }

    @Test
    public void givenToListOptionalDto_whenMapper_ThenCorrect() {
        OptionalDto optionalDtoTest = new OptionalDto(
                optional.getId(),
                optional.getName(),
                optional.getOptionalPrice()
        );

        assertEquals(List.of(optionalDtoTest), optionalMapper.toListOptionalDto(List.of(optional)));
    }
}
