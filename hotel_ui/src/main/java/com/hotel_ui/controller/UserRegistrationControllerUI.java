package com.hotel_ui.controller;

import com.hotel_dto.dto.UserDto;
import com.hotel_ui.message.Messages;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static com.hotel_commons.error.Errors.getBindingResultFromMapErrors;


@RequestMapping("/registration")
@Controller
@AllArgsConstructor
public class UserRegistrationControllerUI {

    private RestTemplate restTemplate;
    private static final String URL_HOST = Messages.getMessage("ui.url.host");
    private static final String URL_REGISTRATION = URL_HOST + Messages.getMessage("ui.url.registration");

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "account/registration";
    }

    @PostMapping
    public String registerUserAccount(@ModelAttribute UserDto userDto, BindingResult bindingResult) {
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(URL_REGISTRATION, userDto, Map.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            var mapErrors = responseEntity.getBody();
            getBindingResultFromMapErrors(mapErrors, bindingResult, "userDto");
            return "account/registration";
        }
        return "redirect:/registration?success";
    }

}
