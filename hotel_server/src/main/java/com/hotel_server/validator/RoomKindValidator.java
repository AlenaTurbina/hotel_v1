package com.hotel_server.validator;

import com.hotel_server.service.RoomKindService;
import com.hotel_dto.dto.RoomKindDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
@AllArgsConstructor
public class RoomKindValidator implements Validator {

    private RoomKindService roomKindService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RoomKindDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoomKindDto roomKindDTO = (RoomKindDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roomType", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "classApartment", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roomPrice", "validation.required");

        if (roomKindDTO.getRoomPrice() <= 0) {
            errors.rejectValue("roomPrice", "validation.field.positive");
        }




        if (roomKindService.getRoomKindIdByRoomTypeIdAndClassApartmentId(roomKindDTO.getRoomType(), roomKindDTO.getClassApartment()) != null) {
            errors.rejectValue("classApartment", "validation.adminSide.roomKind.classApartment");
        }

    }

}
