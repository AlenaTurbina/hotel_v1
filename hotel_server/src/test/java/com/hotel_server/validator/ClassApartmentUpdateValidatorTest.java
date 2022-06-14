package com.hotel_server.validator;

import com.hotel_domain.model.entity.ClassApartment;
import com.hotel_server.service.ClassApartmentService;
import com.hotel_dto.dto.ClassApartmentDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ClassApartmentUpdateValidatorTest {
    @Mock
    private ClassApartmentService classApartmentService;
    @Mock
    private ClassApartment classApartmentExist;
    @Mock
    private ClassApartmentDTO classApartmentDTO;
    @Mock
    private  Errors errors;
    @InjectMocks
    private ClassApartmentUpdateValidator classApartmentUpdateValidator;

    private static final Double testPriceValid = 10.0;
    private static final Double testPriceInvalid = -5.0;
    private static final Double testPriceInvalidZero = 0.0;


    @Test
    void testValidateShouldAcceptClassApartmentPositivePlacePrice() {
        when(classApartmentDTO.getPlacePrice()).thenReturn(testPriceValid);
        classApartmentUpdateValidator.validate(classApartmentDTO, errors);

        verify(errors, never())
                .rejectValue("placePrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectClassApartmentNegativePlacePrice() {
        when(classApartmentDTO.getPlacePrice()).thenReturn(testPriceInvalid);
        classApartmentUpdateValidator.validate(classApartmentDTO, errors);

        verify(errors, times(1))
                .rejectValue("placePrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectClassApartmentZeroPlacePrice() {
        when(classApartmentDTO.getPlacePrice()).thenReturn(testPriceInvalidZero);
        classApartmentUpdateValidator.validate(classApartmentDTO, errors);

        verify(errors, times(1))
                .rejectValue("placePrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldAcceptClassApartmentNewName() {
        when(classApartmentService.getClassApartmentByName(any())).thenReturn(null);
        classApartmentUpdateValidator.validate(classApartmentDTO, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldRejectClassApartmentSameName() {
        when(classApartmentService.getClassApartmentByName(any())).thenReturn(classApartmentExist);
        when(classApartmentDTO.getId()).thenReturn(1);
        when(classApartmentExist.getId()).thenReturn(1);
        classApartmentUpdateValidator.validate(classApartmentDTO, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldRejectClassApartmentSameExistName() {
        when(classApartmentService.getClassApartmentByName(any())).thenReturn(classApartmentExist);
        when(classApartmentDTO.getId()).thenReturn(1);
        when(classApartmentExist.getId()).thenReturn(2);
        classApartmentUpdateValidator.validate(classApartmentDTO, errors);

        verify(errors, times(1))
                .rejectValue("name", "validation.adminSide.duplicateName");
    }
}