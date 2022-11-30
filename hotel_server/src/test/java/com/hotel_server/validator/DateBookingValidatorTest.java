package com.hotel_server.validator;

import com.hotel_dto.dto.OrderBookingDto;
import com.hotel_server.message.Messages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;

import java.time.LocalDate;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DateBookingValidatorTest {
    @Mock
    private OrderBookingDto orderBookingDTO;
    @Mock
    private Errors errors;
    @InjectMocks
    private DateBookingValidator dateBookingValidator;

    private static final Integer MAX_DAYS_DATE_ARRIVAL = Messages.getIntegerMessage("server.booking.maxDaysDateArrival");
    private static final Integer MAX_DAYS_BOOKING_PERIOD = Messages.getIntegerMessage("server.booking.maxDaysBookingPeriod");

    private static final LocalDate dateArrival = LocalDate.of(2022, 01, 10);
    private static final LocalDate dateDepartureValid = LocalDate.of(2022, 01, 12);
    private static final LocalDate dateDepartureInvalid = LocalDate.of(2022, 01, 8);


    @Test
    void testValidateShouldAcceptDatesWhenDateArrivalBeforeDateDeparture() {
        when(orderBookingDTO.getDateArrival()).thenReturn(dateArrival);
        when(orderBookingDTO.getDateDeparture()).thenReturn(dateDepartureValid);
        dateBookingValidator.validate(orderBookingDTO, errors);

        verify(errors, never())
                .rejectValue("dateDeparture", "validation.booking.dateDeparture.min");
    }

    @Test
    void testValidateShouldRejectDatesWhenDateArrivalEqualDateDeparture() {
        when(orderBookingDTO.getDateArrival()).thenReturn(dateArrival);
        when(orderBookingDTO.getDateDeparture()).thenReturn(dateArrival);
        dateBookingValidator.validate(orderBookingDTO, errors);

        verify(errors, times(1))
                .rejectValue("dateDeparture", "validation.booking.dateDeparture.min");
    }

    @Test
    void testValidateShouldRejectDatesWhenDateArrivalAfterDateDeparture() {
        when(orderBookingDTO.getDateArrival()).thenReturn(dateArrival);
        when(orderBookingDTO.getDateDeparture()).thenReturn(dateDepartureInvalid);
        dateBookingValidator.validate(orderBookingDTO, errors);

        verify(errors, times(1))
                .rejectValue("dateDeparture", "validation.booking.dateDeparture.min");
    }

    @Test
    void testValidateShouldAcceptDatesWhenDateArrivalEqualToday() {
        when(orderBookingDTO.getDateArrival()).thenReturn(LocalDate.now());
        when(orderBookingDTO.getDateDeparture()).thenReturn(LocalDate.now().plusDays(1));
        dateBookingValidator.validate(orderBookingDTO, errors);

        verify(errors, never())
                .rejectValue("dateArrival", "validation.booking.dateArrival.min");
    }

    @Test
    void testValidateShouldRejectDatesWhenDateBookingPeriodExceedMax() {
        when(orderBookingDTO.getDateArrival()).thenReturn(dateArrival);
        when(orderBookingDTO.getDateDeparture()).thenReturn(dateArrival.plusDays(MAX_DAYS_BOOKING_PERIOD + 1));
        dateBookingValidator.validate(orderBookingDTO, errors);

        verify(errors, times(1))
                .rejectValue("dateDeparture", "validation.booking.dateDeparture.max");
    }

    @Test
    void testValidateShouldRejectDatesWhenDateArrivalExceedMax() {
        when(orderBookingDTO.getDateArrival()).thenReturn(LocalDate.now().plusDays(MAX_DAYS_DATE_ARRIVAL + 1));
        when(orderBookingDTO.getDateDeparture()).thenReturn(LocalDate.now().plusDays(MAX_DAYS_DATE_ARRIVAL + 2));
        dateBookingValidator.validate(orderBookingDTO, errors);

        verify(errors, times(1))
                .rejectValue("dateArrival", "validation.booking.dateArrival.max");
    }
}