package com.hotel_server.validator;

import com.hotel_domain.model.entity.Optional;
import com.hotel_dto.dto.OptionalDTO;
import com.hotel_server.service.OptionalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OptionalValidatorTest {
    @Mock
    private OptionalDTO optionalDTO;
    @Mock
    private Optional optionalExist;
    @Mock
    private OptionalService optionalService;
    @Mock
    private Errors errors;
    @InjectMocks
    private OptionalValidator optionalValidator;

    private static final Double testPriceValid = 10.0;
    private static final Double testPriceInvalid = -5.0;
    private static final Double testPriceInvalidZero = 0.0;

    @Test
    void testValidateShouldAcceptOptionalDTOPositiveOptionalPrice() {
        when(optionalDTO.getOptionalPrice()).thenReturn(testPriceValid);
        optionalValidator.validate(optionalDTO, errors);

        verify(errors, never())
                .rejectValue("optionalPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectOptionalDTONegativeOptionalPrice() {
        when(optionalDTO.getOptionalPrice()).thenReturn(testPriceInvalid);
        optionalValidator.validate(optionalDTO, errors);

        verify(errors, times(1))
                .rejectValue("optionalPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectOptionalDTOZeroOptionalPrice() {
        when(optionalDTO.getOptionalPrice()).thenReturn(testPriceInvalidZero);
        optionalValidator.validate(optionalDTO, errors);

        verify(errors, times(1))
                .rejectValue("optionalPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldAcceptOptionalNewName() {
        when(optionalService.getOptionalByName(any())).thenReturn(null);
        optionalValidator.validate(optionalDTO, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldRejectOptionalExistName() {
        when(optionalService.getOptionalByName(any())).thenReturn(optionalExist);
        optionalValidator.validate(optionalDTO, errors);

        verify(errors, times(1))
                .rejectValue("name", "validation.adminSide.duplicateName");
    }
}