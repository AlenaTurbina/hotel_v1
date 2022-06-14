package com.hotel_server.validator;

import com.hotel_server.service.RoomKindService;
import com.hotel_dto.dto.RoomKindDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomKindValidatorTest {
    @Mock
    private RoomKindDTO roomKindDTO;
    @Mock
    private RoomKindService roomKindService;
    @Mock
    private Errors errors;
    @InjectMocks
    private RoomKindValidator roomKindValidator;

    private static final Double testPriceValid = 10.0;
    private static final Double testPriceInvalid = -5.0;
    private static final Double testPriceInvalidZero = 0.0;


    @Test
    void testValidateShouldAcceptRoomKindDTOPositivePlacePrice() {
        when(roomKindDTO.getRoomPrice()).thenReturn(testPriceValid);
        roomKindValidator.validate(roomKindDTO, errors);

        verify(errors, never())
                .rejectValue("roomPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectRoomKindDTONegativePlacePrice() {
        when(roomKindDTO.getRoomPrice()).thenReturn(testPriceInvalid);
        roomKindValidator.validate(roomKindDTO, errors);

        verify(errors, times(1))
                .rejectValue("roomPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectRoomKindDTOZeroPlacePrice() {
        when(roomKindDTO.getRoomPrice()).thenReturn(testPriceInvalidZero);
        roomKindValidator.validate(roomKindDTO, errors);

        verify(errors, times(1))
                .rejectValue("roomPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldAcceptRoomKindNewName() {
        when(roomKindService.getRoomKindIdByRoomTypeIdAndClassApartmentId(any(), any())).thenReturn(null);
        roomKindValidator.validate(roomKindDTO, errors);

        verify(errors, never())
                .rejectValue("classApartment", "validation.adminSide.roomKind.classApartment");
    }

    @Test
    void testValidateShouldRejectRoomKindExistName() {
        when(roomKindService.getRoomKindIdByRoomTypeIdAndClassApartmentId(any(), any())).thenReturn(1);
        when(roomKindDTO.getClassApartment()).thenReturn(1);
        when(roomKindDTO.getRoomType()).thenReturn(1);
        roomKindValidator.validate(roomKindDTO, errors);

        verify(errors, times(1))
                .rejectValue("classApartment", "validation.adminSide.roomKind.classApartment");
    }
}