package com.hotel_ui.controller;

import com.hotel_dto.dto.UserDTO;
import com.hotel_ui.message.Messages;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static com.hotel_commons.error.Errors.getBindingResultFromMapErrors;
import static com.hotel_ui.security.UserPrincipal.getPrincipal;


@Controller
@AllArgsConstructor
public class UserControllerUI {

    private RestTemplate restTemplate;
    private static final String URL_HOST = Messages.getMessage("ui.url.host");
    private static final String URL_USERS = URL_HOST + Messages.getMessage("ui.url.users");
    private static final String URL_CLIENT_USER = URL_HOST + Messages.getMessage("ui.url.clientUser");
    private static final String URL_CLIENT_UPDATE = URL_HOST + Messages.getMessage("ui.url.clientUpdate");

    @GetMapping("/admin/users")
    public String getAllUsers(Model model) {
        List<UserDTO> usersDTO = restTemplate.getForObject(URL_USERS, List.class);
        model.addAttribute("users", usersDTO);
        return "admin/users";
    }

    @GetMapping("/client/userData")
    public String userData(Model model) {
        UserDTO user = restTemplate.postForObject(URL_CLIENT_USER, getPrincipal(), UserDTO.class);
        model.addAttribute("user", user);
        return "client/userClient";
    }

    //Update user on client side with validation /GET, POST/
    @GetMapping("/client/updateForm")
    public String updateUserForm(Model model) {
        UserDTO user = restTemplate.postForObject(URL_CLIENT_USER, getPrincipal(), UserDTO.class);
        model.addAttribute("user", user);
        return "client/updateUserClient";
    }

    @PostMapping("/client/updateForm")
    public String updateUser(UserDTO userDTO, Model model, BindingResult bindingResult) {
        ResponseEntity<Map> responseEntity = restTemplate.exchange(URL_CLIENT_UPDATE, HttpMethod.PUT, new HttpEntity<>(userDTO), Map.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            var mapErrors = responseEntity.getBody();
            getBindingResultFromMapErrors(mapErrors, bindingResult, "user");
            model.addAttribute("user", userDTO);
            return "client/updateUserClient";
        }
        return "redirect:/client/userData";
    }
}






