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
class RoomKindUpdateValidatorTest {
    @Mock
    private RoomKindDTO roomKindDTO;
    @Mock
    private RoomKindService roomKindService;
    @Mock
    private Errors errors;
    @InjectMocks
    private RoomKindUpdateValidator roomKindUpdateValidator;

    private static final Double testPriceValid = 10.0;
    private static final Double testPriceInvalid = -5.0;
    private static final Double testPriceInvalidZero = 0.0;


    @Test
    void testValidateShouldAcceptRoomKindPositivePlacePrice() {
        when(roomKindDTO.getRoomPrice()).thenReturn(testPriceValid);
        when(roomKindService.getRoomKindIdByRoomTypeIdAndClassApartmentId(any(), any())).thenReturn(null);
        roomKindUpdateValidator.validate(roomKindDTO, errors);

        verify(errors, never())
                .rejectValue("roomPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectRoomKindNegativePlacePrice() {
        when(roomKindDTO.getRoomPrice()).thenReturn(testPriceInvalid);
        when(roomKindService.getRoomKindIdByRoomTypeIdAndClassApartmentId(any(), any())).thenReturn(null);
        roomKindUpdateValidator.validate(roomKindDTO, errors);

        verify(errors, times(1))
                .rejectValue("roomPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldRejectRoomKindZeroPlacePrice() {
        when(roomKindDTO.getRoomPrice()).thenReturn(testPriceInvalidZero);
        when(roomKindService.getRoomKindIdByRoomTypeIdAndClassApartmentId(any(), any())).thenReturn(null);
        roomKindUpdateValidator.validate(roomKindDTO, errors);

        verify(errors, times(1))
                .rejectValue("roomPrice", "validation.field.positive");
    }

    @Test
    void testValidateShouldAcceptRoomKindNewName() {
        when(roomKindDTO.getRoomPrice()).thenReturn(testPriceValid);
        when(roomKindService.getRoomKindIdByRoomTypeIdAndClassApartmentId(any(), any())).thenReturn(null);
        roomKindUpdateValidator.validate(roomKindDTO, errors);

        verify(errors, never())
                .rejectValue("classApartment", "validation.adminSide.roomKind.classApartment");
    }

    @Test
    void testValidateShouldAcceptRoomKindSameName() {
        when(roomKindDTO.getRoomPrice()).thenReturn(testPriceValid);
        when(roomKindService.getRoomKindIdByRoomTypeIdAndClassApartmentId(any(), any())).thenReturn(1);
        when(roomKindDTO.getClassApartment()).thenReturn(1);
        when(roomKindDTO.getRoomType()).thenReturn(1);
        when(roomKindDTO.getId()).thenReturn(1);
        roomKindUpdateValidator.validate(roomKindDTO, errors);

        verify(errors, never())
                .rejectValue("classApartment", "validation.adminSide.roomKind.classApartment");
    }

    @Test
    void testValidateShouldRejectRoomKindExistName() {
        when(roomKindDTO.getRoomPrice()).thenReturn(testPriceValid);
        when(roomKindService.getRoomKindIdByRoomTypeIdAndClassApartmentId(any(), any())).thenReturn(2);
        when(roomKindDTO.getClassApartment()).thenReturn(1);
        when(roomKindDTO.getRoomType()).thenReturn(1);
        when(roomKindDTO.getId()).thenReturn(1);
        roomKindUpdateValidator.validate(roomKindDTO, errors);

        verify(errors, times(1))
                .rejectValue("classApartment", "validation.adminSide.roomKind.classApartment");
    }
}