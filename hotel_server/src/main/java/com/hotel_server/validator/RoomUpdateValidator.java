package com.hotel_server.validator;

import com.hotel_domain.model.entity.Room;
import com.hotel_server.service.RoomService;
import com.hotel_dto.dto.RoomDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.UUID;


@Component
@AllArgsConstructor
public class RoomUpdateValidator implements Validator {
    RoomService roomService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RoomDto.class.equals(clazz);}

    @Override
    public void validate(Object target, Errors errors) {
        RoomDto roomDTO = (RoomDto) target;

        UUID checkID = checkRoom(roomDTO);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roomKind", "validation.required");

        if (checkID != null && checkID != roomDTO.getId()) {
            errors.rejectValue("name", "validation.adminSide.duplicateName");
        }

    }

    public UUID checkRoom(RoomDto roomDTO) {
        Room findRoom = roomService.getRoomByName(roomDTO.getName());
        if (findRoom != null) {
            return findRoom.getId();
        }
        return null;
    }

}
