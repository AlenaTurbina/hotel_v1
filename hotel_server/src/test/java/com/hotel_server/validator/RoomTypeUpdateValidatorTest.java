package com.hotel_server.validator;

import com.hotel_domain.model.entity.RoomType;
import com.hotel_server.service.RoomTypeService;
import com.hotel_dto.dto.RoomTypeDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomTypeUpdateValidatorTest {
    @Mock
    private RoomTypeDTO roomTypeDTO;
    @Mock
    private RoomType roomTypeExist;
    @Mock
    private RoomTypeService roomTypeService;
    @Mock
    private Errors errors;
    @InjectMocks
    private RoomTypeUpdateValidator roomTypeUpdateValidator;

    private static final Integer quantityPlacesValid = 5;
    private static final Integer quantityPlacesOneValid = 1;
    private static final Integer quantityPlacesInvalidZero = 0;


    @Test
    void testValidateShouldAcceptRoomTypeQuantityPlacesValid() {
        when(roomTypeDTO.getQuantityPlaces()).thenReturn(quantityPlacesValid);
        roomTypeUpdateValidator.validate(roomTypeDTO, errors);

        verify(errors, never())
                .rejectValue("quantityPlaces", "validation.field.positive");
    }

    @Test
    void testValidateShouldAcceptRoomTypeQuantityPlacesOneValid() {
        when(roomTypeDTO.getQuantityPlaces()).thenReturn(quantityPlacesOneValid);
        roomTypeUpdateValidator.validate(roomTypeDTO, errors);

        verify(errors, never())
                .rejectValue("quantityPlaces", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectRoomTypeZeroQuantityPlaces() {
        when(roomTypeDTO.getQuantityPlaces()).thenReturn(quantityPlacesInvalidZero);
        roomTypeUpdateValidator.validate(roomTypeDTO, errors);

        verify(errors, times(1))
                .rejectValue("quantityPlaces", "validation.field.positive");
    }

    @Test
    void testValidateShouldAcceptRoomTypeNewName() {
        when(roomTypeService.getRoomTypeByName(any())).thenReturn(null);
        roomTypeUpdateValidator.validate(roomTypeDTO, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldRejectRoomTypeSameName() {
        when(roomTypeService.getRoomTypeByName(any())).thenReturn(roomTypeExist);
        when(roomTypeDTO.getId()).thenReturn(1);
        when(roomTypeExist.getId()).thenReturn(1);
        roomTypeUpdateValidator.validate(roomTypeDTO, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldRejectRoomTypeSameExistName() {
        when(roomTypeService.getRoomTypeByName(any())).thenReturn(roomTypeExist);
        when(roomTypeDTO.getId()).thenReturn(1);
        when(roomTypeExist.getId()).thenReturn(2);
        roomTypeUpdateValidator.validate(roomTypeDTO, errors);

        verify(errors, times(1))
                .rejectValue("name", "validation.adminSide.duplicateName");
    }
}