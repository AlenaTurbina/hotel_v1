package com.hotel_server.contollerRest;

import com.hotel_database.model.repository.UserRepository;
import com.hotel_dto.dto.UserDTO;
import com.hotel_dto.mapper.UserMapper;
import com.hotel_server.service.UserService;
import com.hotel_server.validator.UserClientUpdateValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name="User", description = "Management of User - getting list and update")
public class UserRestController {
    private UserService userService;
    private UserMapper userMapper;
    private UserRepository userRepository;
    private UserClientUpdateValidator userClientUpdateValidator;

    @InitBinder(value = "userDTO")
    void initUserClientUpdateValidator(WebDataBinder binder) {
        binder.setValidator(userClientUpdateValidator);
    }

    @Operation(summary = "Getting list of users")
    @GetMapping("/admin/users")
    List<UserDTO> getAllUsers() {
        return userMapper.toListUserDTO(userService.getAllUsers());
    }

    @Operation(summary = "Getting user by email")
    @PostMapping("/client/user")
    UserDTO getUserOnEmail(@RequestBody String email) {
        return userMapper.toUserDTO(userService.getUserByEmail(email));
    }

    @Operation(summary = "Updating user")
    @PutMapping("/client/user/update")
    ResponseEntity updateClientUser(@RequestBody @Valid UserDTO userDTO) {
        return new ResponseEntity<>(userMapper.toUserDTO(userService.updateUser(userDTO)), HttpStatus.CREATED);
    }

    @Operation(summary = "Checking user's authentication")
    @PostMapping("/auth")
    UserDTO checkAuthentication(@RequestBody String email){
        return userMapper.toUserDTOAuth(userRepository.findByEmail(email));
   }

}
