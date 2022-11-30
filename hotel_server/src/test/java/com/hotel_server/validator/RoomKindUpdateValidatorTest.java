package com.hotel_server.validator;

import com.hotel_server.service.RoomKindService;
import com.hotel_dto.dto.RoomKindDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomKindUpdateValidatorTest {
    @Mock
    private RoomKindDto roomKindDTO;
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
        UUID uuid = UUID.randomUUID();
        when(roomKindDTO.getRoomPrice()).thenReturn(testPriceValid);
        when(roomKindService.getRoomKindIdByRoomTypeIdAndClassApartmentId(any(), any())).thenReturn(uuid);
        when(roomKindDTO.getClassApartment()).thenReturn(UUID.randomUUID());
        when(roomKindDTO.getRoomType()).thenReturn(UUID.randomUUID());
        when(roomKindDTO.getId()).thenReturn(uuid);
        roomKindUpdateValidator.validate(roomKindDTO, errors);

        verify(errors, never())
                .rejectValue("classApartment", "validation.adminSide.roomKind.classApartment");
    }

    @Test
    void testValidateShouldRejectRoomKindExistName() {
        when(roomKindDTO.getRoomPrice()).thenReturn(testPriceValid);
        when(roomKindService.getRoomKindIdByRoomTypeIdAndClassApartmentId(any(), any())).thenReturn(UUID.randomUUID());
        when(roomKindDTO.getClassApartment()).thenReturn(UUID.randomUUID());
        when(roomKindDTO.getRoomType()).thenReturn(UUID.randomUUID());
        when(roomKindDTO.getId()).thenReturn(UUID.randomUUID());
        roomKindUpdateValidator.validate(roomKindDTO, errors);

        verify(errors, times(1))
                .rejectValue("classApartment", "validation.adminSide.roomKind.classApartment");
    }
}