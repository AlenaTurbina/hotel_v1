package com.hotel_server.validator;

import com.hotel_domain.model.entity.Optional;
import com.hotel_server.service.OptionalService;
import com.hotel_dto.dto.OptionalDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OptionalUpdateValidatorTest {
    @Mock
    OptionalDTO optionalDTO;
    @Mock
    Optional optionalExist;
    @Mock
    private OptionalService optionalService;
    @Mock
    private Errors errors;
    @InjectMocks
    private OptionalUpdateValidator optionalUpdateValidator;

    private static final Double testPriceValid = 1.0;
    private static final Double testPriceInvalid = -1.0;
    private static final Double testPriceZero = 0.0;


    @Test
    void testValidateShouldAcceptOptionalPositivePrice() {
        when(optionalDTO.getOptionalPrice()).thenReturn(testPriceValid);
        optionalUpdateValidator.validate(optionalDTO, errors);

        verify(errors, never())
                .rejectValue("optionalPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectOptionalNegativePrice() {
        when(optionalDTO.getOptionalPrice()).thenReturn(testPriceInvalid);
        optionalUpdateValidator.validate(optionalDTO, errors);

        verify(errors, times(1))
                .rejectValue("optionalPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectOptionalZeroPrice() {
        when(optionalDTO.getOptionalPrice()).thenReturn(testPriceZero);
        optionalUpdateValidator.validate(optionalDTO, errors);

        verify(errors, times(1))
                .rejectValue("optionalPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldAcceptOptionalNewName() {
        when(optionalService.getOptionalByName(any())).thenReturn(null);
        optionalUpdateValidator.validate(optionalDTO, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldRejectOptionalSameName() {
        when(optionalService.getOptionalByName(any())).thenReturn(optionalExist);
        when(optionalDTO.getId()).thenReturn(1);
        when(optionalExist.getId()).thenReturn(1);
        optionalUpdateValidator.validate(optionalDTO, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldRejectOptionalSameExistName() {
        when(optionalService.getOptionalByName(any())).thenReturn(optionalExist);
        when(optionalDTO.getId()).thenReturn(1);
        when(optionalExist.getId()).thenReturn(2);
        optionalUpdateValidator.validate(optionalDTO, errors);

        verify(errors, times(1))
                .rejectValue("name", "validation.adminSide.duplicateName");
    }
}