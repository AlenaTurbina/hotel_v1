package com.hotel_server.validator;

import com.hotel_domain.model.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserUpdateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userStatus", "validation.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roles", "validation.required");

    }
}
