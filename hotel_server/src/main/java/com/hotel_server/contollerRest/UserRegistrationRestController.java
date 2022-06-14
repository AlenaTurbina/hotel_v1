package com.hotel_server.contollerRest;

import com.hotel_dto.dto.UserDTO;
import com.hotel_dto.mapper.UserMapper;
import com.hotel_server.service.UserService;
import com.hotel_server.validator.UserValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name="User registration", description = "Management of Users registration")
public class UserRegistrationRestController {
    UserService userService;
    UserMapper userMapper;
    UserValidator userValidator;

    @InitBinder(value = "userDTO")
    void initUserRegistrationValidator(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }


    @Operation(summary = "Registration of User")
    @PostMapping("/registration")
    ResponseEntity registerUserAccount(@RequestBody @Valid UserDTO userDTO) {
        userService.saveUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
