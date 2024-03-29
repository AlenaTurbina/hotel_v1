package com.hotel_server.validator;

import com.hotel_domain.model.entity.ClassApartment;
import com.hotel_server.service.ClassApartmentService;
import com.hotel_dto.dto.ClassApartmentDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.UUID;


@Component
@AllArgsConstructor
public class ClassApartmentUpdateValidator implements Validator {
    ClassApartmentService classApartmentService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ClassApartmentDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
       ClassApartmentDto classApartmentDTO = (ClassApartmentDto) target;

//        Integer checkID = checkClassApartment(classApartmentDTO);
        UUID checkID = checkClassApartment(classApartmentDTO);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "placePrice", "validation.required");

        if (classApartmentDTO.getPlacePrice() <= 0) {
            errors.rejectValue("placePrice", "validation.field.positive");
        }


        if (checkID != null && checkID != classApartmentDTO.getId()) {
            errors.rejectValue("name", "validation.adminSide.duplicateName");
        }
    }

    public UUID checkClassApartment(ClassApartmentDto classApartmentDTO) {
        ClassApartment findClassApartment = classApartmentService.getClassApartmentByName(classApartmentDTO.getName());
        if (findClassApartment != null) {
            return findClassApartment.getId();
        }
        return null;
    }


}
