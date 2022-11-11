package com.hotel_server.validator;

import com.hotel_server.message.Messages;
import com.hotel_domain.model.entity.RoomType;
import com.hotel_server.service.RoomTypeService;
import com.hotel_dto.dto.OrderBookingDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderBookingValidatorTest {
    @Mock
    private OrderBookingDTO orderBookingDTO;
    @Mock
    private RoomType roomType;
    @Mock
    private Errors errors;
    @Mock
    private RoomTypeService roomTypeService;
    @InjectMocks
    private OrderBookingValidator orderBookingValidator;

    private static final Integer MAX_DAYS_DATE_ARRIVAL = Messages.getIntegerMessage("server.booking.maxDaysDateArrival");
    private static final Integer MAX_DAYS_BOOKING_PERIOD = Messages.getIntegerMessage("server.booking.maxDaysBookingPeriod");

    private static final LocalDate dateArrival = LocalDate.of(2022, 01, 10);
    private static final LocalDate dateDepartureValid = LocalDate.of(2022, 01, 12);
    private static final LocalDate dateDepartureInvalid = LocalDate.of(2022, 01, 8);


    @Test
    void testValidateShouldAcceptDatesWhenDateArrivalBeforeDateDeparture() {
        when(orderBookingDTO.getDateArrival()).thenReturn(dateArrival);
        when(orderBookingDTO.getDateDeparture()).thenReturn(dateDepartureValid);
        when(roomTypeService.getRoomTypeById(any())).thenReturn(roomType);
        when(orderBookingDTO.getQuantityPersons()).thenReturn(1);
        when(roomType.getQuantityPlaces()).thenReturn(1);
        orderBookingValidator.validate(orderBookingDTO, errors);

        verify(errors, never())
                .rejectValue("dateDeparture", "validation.booking.dateDeparture.min");
    }

    @Test
    void testValidateShouldRejectDatesWhenDateArrivalEqualDateDeparture() {
        when(orderBookingDTO.getDateArrival()).thenReturn(dateArrival);
        when(orderBookingDTO.getDateDeparture()).thenReturn(dateArrival);
        when(roomTypeService.getRoomTypeById(any())).thenReturn(roomType);
        when(orderBookingDTO.getQuantityPersons()).thenReturn(1);
        when(roomType.getQuantityPlaces()).thenReturn(1);
        orderBookingValidator.validate(orderBookingDTO, errors);

        verify(errors, times(1))
                .rejectValue("dateDeparture", "validation.booking.dateDeparture.min");
    }

    @Test
    void testValidateShouldRejectDatesWhenDateArrivalAfterDateDeparture() {
        when(orderBookingDTO.getDateArrival()).thenReturn(dateArrival);
        when(orderBookingDTO.getDateDeparture()).thenReturn(dateDepartureInvalid);
        when(roomTypeService.getRoomTypeById(any())).thenReturn(roomType);
        when(orderBookingDTO.getQuantityPersons()).thenReturn(1);
        when(roomType.getQuantityPlaces()).thenReturn(1);
        orderBookingValidator.validate(orderBookingDTO, errors);

        verify(errors, times(1))
                .rejectValue("dateDeparture", "validation.booking.dateDeparture.min");
    }

    @Test
    void testValidateShouldAcceptDatesWhenDateArrivalEqualToday() {
        when(orderBookingDTO.getDateArrival()).thenReturn(LocalDate.now());
        when(orderBookingDTO.getDateDeparture()).thenReturn(LocalDate.now().plusDays(1));
        when(roomTypeService.getRoomTypeById(any())).thenReturn(roomType);
        when(orderBookingDTO.getQuantityPersons()).thenReturn(1);
        when(roomType.getQuantityPlaces()).thenReturn(1);
        orderBookingValidator.validate(orderBookingDTO, errors);

        verify(errors, never())
                .rejectValue("dateArrival", "validation.booking.dateArrival.min");
    }

    @Test
    void testValidateShouldRejectDatesWhenDateBookingPeriodExceedMax() {
        when(orderBookingDTO.getDateArrival()).thenReturn(dateArrival);
        when(orderBookingDTO.getDateDeparture()).thenReturn(dateArrival.plusDays(MAX_DAYS_BOOKING_PERIOD + 1));
        when(roomTypeService.getRoomTypeById(any())).thenReturn(roomType);
        when(orderBookingDTO.getQuantityPersons()).thenReturn(1);
        when(roomType.getQuantityPlaces()).thenReturn(1);
        orderBookingValidator.validate(orderBookingDTO, errors);

        verify(errors, times(1))
                .rejectValue("dateDeparture", "validation.booking.dateDeparture.max");
    }

    @Test
    void testValidateShouldRejectDatesWhenDateArrivalExceedMax() {
        when(orderBookingDTO.getDateArrival()).thenReturn(LocalDate.now().plusDays(MAX_DAYS_DATE_ARRIVAL + 1));
        when(orderBookingDTO.getDateDeparture()).thenReturn(LocalDate.now().plusDays(MAX_DAYS_DATE_ARRIVAL + 2));
        when(roomTypeService.getRoomTypeById(any())).thenReturn(roomType);
        when(orderBookingDTO.getQuantityPersons()).thenReturn(1);
        when(roomType.getQuantityPlaces()).thenReturn(1);
        orderBookingValidator.validate(orderBookingDTO, errors);

        verify(errors, times(1))
                .rejectValue("dateArrival", "validation.booking.dateArrival.max");
    }

    @Test
    void testValidateShouldRejectFailQuantityGuests() {
        when(orderBookingDTO.getDateArrival()).thenReturn(dateArrival);
        when(orderBookingDTO.getDateDeparture()).thenReturn(dateDepartureValid);
        when(roomTypeService.getRoomTypeById(any())).thenReturn(roomType);
        when(orderBookingDTO.getQuantityPersons()).thenReturn(2);
        when(roomType.getQuantityPlaces()).thenReturn(1);
        orderBookingValidator.validate(orderBookingDTO, errors);

        verify(errors, times(1))
                .rejectValue("roomType", "validation.booking.quantityPersons.max");
    }
}