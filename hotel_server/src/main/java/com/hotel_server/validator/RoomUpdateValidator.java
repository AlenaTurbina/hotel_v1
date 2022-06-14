package com.hotel_server.validator;

import com.hotel_domain.model.entity.Room;
import com.hotel_server.service.RoomService;
import com.hotel_dto.dto.RoomDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
@AllArgsConstructor
public class RoomUpdateValidator implements Validator {
    RoomService roomService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RoomDTO.class.equals(clazz);}

    @Override
    public void validate(Object target, Errors errors) {
        RoomDTO roomDTO = (RoomDTO) target;

        Integer checkID = checkRoom(roomDTO);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roomKind", "validation.required");

        if (checkID != null && checkID != roomDTO.getId()) {
            errors.rejectValue("name", "validation.adminSide.duplicateName");
        }

    }

    public Integer checkRoom(RoomDTO roomDTO) {
        Room findRoom = roomService.getRoomByName(roomDTO.getName());
        if (findRoom != null) {
            return findRoom.getId();
        }
        return null;
    }

}
