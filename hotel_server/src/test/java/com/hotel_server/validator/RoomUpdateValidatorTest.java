package com.hotel_server.validator;

import com.hotel_domain.model.entity.Room;
import com.hotel_server.service.RoomService;
import com.hotel_dto.dto.RoomDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoomUpdateValidatorTest {
    @Mock
    private RoomService roomService;
    @Mock
    private RoomDTO roomDTO;
    @Mock
    private Room roomExist;
    @Mock
    private Errors errors;
    @InjectMocks
    private RoomUpdateValidator roomUpdateValidator;


    @Test
    void testValidateShouldAcceptRoomNewName() {
        when(roomService.getRoomByName(any())).thenReturn(null);
        roomUpdateValidator.validate(roomDTO, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldAcceptRoomSameName() {
        when(roomService.getRoomByName(any())).thenReturn(roomExist);
        when(roomDTO.getId()).thenReturn(1);
        when(roomExist.getId()).thenReturn(1);
        roomUpdateValidator.validate(roomDTO, errors);

        verify(errors, never())
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

    @Test
    void testValidateShouldRejectRoomSameExistName() {
        when(roomService.getRoomByName(any())).thenReturn(roomExist);
        when(roomDTO.getId()).thenReturn(1);
        when(roomExist.getId()).thenReturn(2);
        roomUpdateValidator.validate(roomDTO, errors);

        verify(errors, times(1))
                .rejectValue("name", "validation.adminSide.duplicateName");
    }

}