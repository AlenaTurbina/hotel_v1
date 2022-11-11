package com.hotel_server.validator;

import com.hotel_domain.model.entity.User;
import com.hotel_dto.dto.UserDTO;
import com.hotel_server.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUpdateValidatorTest {
    @Mock
    private UserService userService;
    @Mock
    private UserDTO userDTO;
    @Mock
    private User userExist;
    @Mock
    private Errors errors;
    @InjectMocks
    private UserClientUpdateValidator userClientUpdateValidator;

    private static final String firstNameValid = "Ivan";
    private static final String firstNameInvalid = "I";
    private static final String lastNameValid = "Ivanov";
    private static final String lastNameInvalid = "I";
    private static final String emailValid = "test@test.com";
    private static final String emailInvalid = "t.@test.com";
    private static final String phoneNumberValid = "+1255555555555";
    private static final String phoneNumberInvalid = "+125";
    private static final String password = "123";
    private static final String passwordInvalid = "1A";

    @Test
    void testValidateShouldRejectUserInvalidFirstName() {
        when(userDTO.getFirstName()).thenReturn(firstNameInvalid);

        when(userDTO.getLastName()).thenReturn(lastNameValid);
        when(userDTO.getEmail()).thenReturn(emailValid);
        when(userDTO.getPhoneNumber()).thenReturn(phoneNumberValid);
        when(userDTO.getPassword()).thenReturn(password);
        when(userService.getUserByEmail(any())).thenReturn(null);
        userClientUpdateValidator.validate(userDTO, errors);

        verify(errors, times(1))
                .rejectValue("firstName", "validation.size.registration.userName");
    }

    @Test
    void testValidateShouldRejectUserInvalidLastName() {
        when(userDTO.getLastName()).thenReturn(lastNameInvalid);

        when(userDTO.getFirstName()).thenReturn(firstNameValid);
        when(userDTO.getEmail()).thenReturn(emailValid);
        when(userDTO.getPhoneNumber()).thenReturn(phoneNumberValid);
        when(userDTO.getPassword()).thenReturn(password);
        when(userService.getUserByEmail(any())).thenReturn(null);
        userClientUpdateValidator.validate(userDTO, errors);

        verify(errors, times(1))
                .rejectValue("lastName", "validation.size.registration.userName");
    }

    @Test
    void testValidateShouldRejectUserInvalidEmail() {
        when(userDTO.getEmail()).thenReturn(emailInvalid);

        when(userDTO.getFirstName()).thenReturn(firstNameValid);
        when(userDTO.getLastName()).thenReturn(lastNameValid);
        when(userDTO.getPhoneNumber()).thenReturn(phoneNumberValid);
        when(userDTO.getPassword()).thenReturn(password);
        when(userService.getUserByEmail(any())).thenReturn(null);
        userClientUpdateValidator.validate(userDTO, errors);

        verify(errors, times(1))
                .rejectValue("email", "validation.pattern.registration.email");
    }

    @Test
    void testValidateShouldRejectUserInvalidPhoneNumber() {
        when(userDTO.getPhoneNumber()).thenReturn(phoneNumberInvalid);

        when(userDTO.getFirstName()).thenReturn(firstNameValid);
        when(userDTO.getLastName()).thenReturn(lastNameValid);
        when(userDTO.getEmail()).thenReturn(emailValid);
        when(userDTO.getPassword()).thenReturn(password);
        when(userService.getUserByEmail(any())).thenReturn(null);
        userClientUpdateValidator.validate(userDTO, errors);

        verify(errors, times(1))
                .rejectValue("phoneNumber", "validation.pattern.registration.phoneNumber");
    }

    @Test
    void testValidateShouldRejectUserPasswordInvalid() {
        when(userDTO.getPassword()).thenReturn(passwordInvalid);

        when(userDTO.getFirstName()).thenReturn(firstNameValid);
        when(userDTO.getLastName()).thenReturn(lastNameValid);
        when(userDTO.getEmail()).thenReturn(emailValid);
        when(userDTO.getPhoneNumber()).thenReturn(phoneNumberValid);
        when(userService.getUserByEmail(any())).thenReturn(null);
        userClientUpdateValidator.validate(userDTO, errors);

        verify(errors, times(1))
                .rejectValue("password", "validation.size.registration.password");
    }

    @Test
    void testValidateShouldAcceptUserEmailSame() {
        UUID uuid = UUID.randomUUID();
        when(userService.getUserByEmail(any())).thenReturn(userExist);
        when(userExist.getId()).thenReturn(uuid);
        when(userDTO.getId()).thenReturn(uuid);

        when(userDTO.getFirstName()).thenReturn(firstNameValid);
        when(userDTO.getLastName()).thenReturn(lastNameValid);
        when(userDTO.getEmail()).thenReturn(emailValid);
        when(userDTO.getPhoneNumber()).thenReturn(phoneNumberValid);
        when(userDTO.getPassword()).thenReturn(password);
        userClientUpdateValidator.validate(userDTO, errors);

        verify(errors, never())
                .rejectValue("email", "validation.duplicate.registration.email");
    }

    @Test
    void testValidateShouldAcceptUserEmailNew() {
        when(userService.getUserByEmail(any())).thenReturn(null);

        when(userDTO.getFirstName()).thenReturn(firstNameValid);
        when(userDTO.getLastName()).thenReturn(lastNameValid);
        when(userDTO.getEmail()).thenReturn(emailValid);
        when(userDTO.getPhoneNumber()).thenReturn(phoneNumberValid);
        when(userDTO.getPassword()).thenReturn(password);
        userClientUpdateValidator.validate(userDTO, errors);

        verify(errors, never())
                .rejectValue("email", "validation.duplicate.registration.email");
    }

    @Test
    void testValidateShouldRejectUserEmailExist() {
        UUID uuid = UUID.randomUUID();
        UUID uuidExist = UUID.randomUUID();
        when(userService.getUserByEmail(any())).thenReturn(userExist);
        when(userExist.getId()).thenReturn(uuidExist);
        when(userDTO.getId()).thenReturn(uuid);

        when(userDTO.getFirstName()).thenReturn(firstNameValid);
        when(userDTO.getLastName()).thenReturn(lastNameValid);
        when(userDTO.getEmail()).thenReturn(emailValid);
        when(userDTO.getPhoneNumber()).thenReturn(phoneNumberValid);
        when(userDTO.getPassword()).thenReturn(password);
        userClientUpdateValidator.validate(userDTO, errors);

        verify(errors, times(1))
                .rejectValue("email", "validation.duplicate.registration.email");
    }
}