package com.hotel_server.validator;

import com.hotel_domain.model.entity.Optional;
import com.hotel_server.service.OptionalService;
import com.hotel_dto.dto.OptionalDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
@AllArgsConstructor
public class OptionalUpdateValidator implements Validator {
    OptionalService optionalService;

    @Override
    public boolean supports(Class<?> clazz) {
        return OptionalDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OptionalDTO optionalDTO = (OptionalDTO) target;

        Integer checkID = checkOptional(optionalDTO);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "optionalPrice", "validation.required");

        if (optionalDTO.getOptionalPrice() <= 0) {
            errors.rejectValue("optionalPrice", "validation.field.positive");
        }

        if (checkID != null && checkID != optionalDTO.getId()) {
            errors.rejectValue("name", "validation.adminSide.duplicateName");
        }

    }

    public Integer checkOptional(OptionalDTO optionalDTO) {
        Optional findOptional = optionalService.getOptionalByName(optionalDTO.getName());
        if (findOptional != null) {
            return findOptional.getId();
        }
        return null;
    }

}
