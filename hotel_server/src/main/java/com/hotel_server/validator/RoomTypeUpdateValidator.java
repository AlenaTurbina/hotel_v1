package com.hotel_server.validator;

import com.hotel_domain.model.entity.RoomType;
import com.hotel_server.service.RoomTypeService;
import com.hotel_dto.dto.RoomTypeDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.UUID;


@Component
@AllArgsConstructor
public class RoomTypeUpdateValidator implements Validator {
    RoomTypeService roomTypeService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RoomTypeDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RoomTypeDto roomTypeDTO = (RoomTypeDto) target;

        UUID checkID = checkID(roomTypeDTO);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantityPlaces", "validation.required");


        if (checkID != null && checkID != roomTypeDTO.getId()) {
            errors.rejectValue("name", "validation.adminSide.duplicateName");
        }

        if (roomTypeDTO.getQuantityPlaces() < 1) {
            errors.rejectValue("quantityPlaces", "validation.field.positive");
        }

    }

    public UUID checkID(RoomTypeDto roomTypeDTO) {
        RoomType findRoomType = roomTypeService.getRoomTypeByName(roomTypeDTO.getName());
        if (findRoomType != null) {
            return findRoomType.getId();
        }
        return null;
    }

}
