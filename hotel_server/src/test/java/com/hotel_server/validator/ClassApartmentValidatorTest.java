package com.hotel_server.validator;

import com.hotel_domain.model.entity.ClassApartment;
import com.hotel_dto.dto.ClassApartmentDto;
import com.hotel_server.service.ClassApartmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ClassApartmentValidatorTest {
    @Mock
    private ClassApartmentDto classApartmentDTO;
    @Mock
    private ClassApartmentService classApartmentService;
    @Mock
    private ClassApartment classApartmentExist;
    @Mock
    private Errors errors;
    @InjectMocks
    private ClassApartmentValidator classApartmentValidator;

    private static final Double testPriceValid = 10.0;
    private static final Double testPriceInvalid = -5.0;
    private static final Double testPriceInvalidZero = 0.0;


    @Test
    void testValidateShouldAcceptClassApartmentDTOPositivePlacePrice() {
        when(classApartmentDTO.getPlacePrice()).thenReturn(testPriceValid);
        classApartmentValidator.validate(classApartmentDTO, errors);

        verify(errors, never())
                .rejectValue("placePrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectClassApartmentDTONegativePlacePrice() {
        when(classApartmentDTO.getPlacePrice()).thenReturn(testPriceInvalid);
        classApartmentValidator.validate(classApartmentDTO, errors);

        verify(errors, times(1))
                .rejectValue("placePrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectClassApartmentDTOZeroPlacePrice() {
        when(classApartmentDTO.getPlacePrice()).thenReturn(testPriceInvalidZero);
        classApartmentValidator.validate(classApartmentDTO, errors);

        verify(errors, times(1))
                .rejectValue("placePrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldAcceptClassApartmentNewName() {
        when(classApartmentService.getClassApartmentByName(any())).thenReturn(null);
        classApartmentValidator.validate(classApartmentDTO, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldRejectClassApartmentExistName() {
        when(classApartmentService.getClassApartmentByName(any())).thenReturn(classApartmentExist);
        classApartmentValidator.validate(classApartmentDTO, errors);

        verify(errors, times(1))
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

}