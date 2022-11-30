package com.hotel_server.validator;

import com.hotel_server.message.Messages;
import com.hotel_dto.dto.OrderBookingDto;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.time.LocalDate;



@Component
@NoArgsConstructor
public class DateBookingValidator implements Validator {

    private static final Integer MAX_DAYS_DATE_ARRIVAL = Messages.getIntegerMessage("server.booking.maxDaysDateArrival");
    private static final Integer MAX_DAYS_BOOKING_PERIOD = Messages.getIntegerMessage("server.booking.maxDaysBookingPeriod");

    @Override
    public boolean supports(Class<?> clazz) {
        return OrderBookingDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    OrderBookingDto orderBookingDTO = (OrderBookingDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateArrival", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "dateDeparture", "validation.required");

        if(LocalDate.now().isAfter(orderBookingDTO.getDateArrival())){
            errors.rejectValue("dateArrival", "validation.booking.dateArrival.min");
        }

        if(!orderBookingDTO.getDateDeparture().isAfter(orderBookingDTO.getDateArrival())){
            errors.rejectValue("dateDeparture", "validation.booking.dateDeparture.min");
        }

        if(orderBookingDTO.getDateArrival().isAfter(LocalDate.now().plusDays(MAX_DAYS_DATE_ARRIVAL))){
            errors.rejectValue("dateArrival", "validation.booking.dateArrival.max");
        }

        if(orderBookingDTO.getDateDeparture().isAfter(orderBookingDTO.getDateArrival().plusDays(MAX_DAYS_BOOKING_PERIOD))){
            errors.rejectValue("dateDeparture", "validation.booking.dateDeparture.max");
        }

    }
}
