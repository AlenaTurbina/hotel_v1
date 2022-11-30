package com.hotel_server.validator;

import com.hotel_server.service.OptionalService;
import com.hotel_dto.dto.OptionalDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
@AllArgsConstructor
public class OptionalValidator implements Validator {
    OptionalService optionalService;

    @Override
    public boolean supports(Class<?> clazz) {

        return OptionalDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OptionalDto optionalDTO = (OptionalDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "optionalPrice", "validation.required");

        if (optionalDTO.getOptionalPrice() <= 0) {
            errors.rejectValue("optionalPrice", "validation.field.positive");
        }

        if (optionalService.getOptionalByName(optionalDTO.getName()) != null) {
            errors.rejectValue("name", "validation.adminSide.duplicateName");
        }
    }

}
