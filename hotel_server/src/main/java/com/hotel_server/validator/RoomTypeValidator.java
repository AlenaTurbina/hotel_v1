package com.hotel_server.validator;

import com.hotel_server.service.RoomTypeService;
import com.hotel_dto.dto.RoomTypeDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
@AllArgsConstructor
public class RoomTypeValidator implements Validator {
    RoomTypeService roomTypeService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RoomTypeDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoomTypeDTO roomTypeDTO = (RoomTypeDTO) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantityPlaces", "validation.required");

        if (roomTypeDTO.getQuantityPlaces() < 1) {
            errors.rejectValue("quantityPlaces", "validation.field.positive");
        }

        if (roomTypeService.getRoomTypeByName(roomTypeDTO.getName()) !=null) {
            errors.rejectValue("name", "validation.adminSide.duplicateName");
        }
    }

}
