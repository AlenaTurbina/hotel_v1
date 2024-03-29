package com.hotel_server.validator;

import com.hotel_server.service.RoomService;
import com.hotel_dto.dto.RoomDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
@AllArgsConstructor
public class RoomValidator implements Validator {
    private RoomService roomService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RoomDto.class.equals(clazz);}

    @Override
    public void validate(Object target, Errors errors) {
        RoomDto roomDTO = (RoomDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roomKind", "validation.required");

        if (roomService.getRoomByName(roomDTO.getName()) !=null) {
            errors.rejectValue("name", "validation.adminSide.duplicateName");
        }
        

    }

}
