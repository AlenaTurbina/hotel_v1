package com.hotel_ui.controller;

import com.hotel_ui.message.Messages;
import com.hotel_dto.dto.OptionalDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static com.hotel_commons.error.Errors.getBindingResultFromMapErrors;


@RequestMapping("/admin/optionals")
@Controller
@AllArgsConstructor
public class OptionalControllerUI {

    private RestTemplate restTemplate;
    private static final String URL_HOST = Messages.getMessage("ui.url.host");
    private static final String URL_OPTIONALS = URL_HOST + Messages.getMessage("ui.url.optionals");

    //List of Optionals /GET/
    @GetMapping()
    private String getAllOptionals(Model model) {
        model.addAttribute("optionals", restTemplate.getForObject(URL_OPTIONALS, List.class));
        return "admin/optionals";
    }

    //Create optional /GET, POST/
    @GetMapping("/create")
    private String createOptionalForm(Model model) {
        model.addAttribute("optionalDTO", new OptionalDTO());
        return "admin/createOptionals";
    }

    @PostMapping("/create")
    private String createOptional(@ModelAttribute OptionalDTO optionalDTO, BindingResult bindingResult) {
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(URL_OPTIONALS, optionalDTO, Map.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            var mapErrors = responseEntity.getBody();
            getBindingResultFromMapErrors(mapErrors, bindingResult, "optionalDTO");
            return "admin/createOptionals";
        }
        return "redirect:/admin/optionals";
    }

    //Update optional /GET, POST/
    @GetMapping("/update/{id}")
    public String updateOptionalsForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("optionalDTO", restTemplate.getForObject((URL_OPTIONALS + "/" + id), OptionalDTO.class));
        return "admin/updateOptionals";
    }

    @PostMapping("/update")
    public String updateOptionals(@ModelAttribute OptionalDTO optionalDTO, BindingResult bindingResult, Model model) {
        ResponseEntity<Map> responseEntity = restTemplate.exchange(URL_OPTIONALS, HttpMethod.PUT, new HttpEntity<>(optionalDTO), Map.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            var mapErrors = responseEntity.getBody();
            model.addAttribute("optionalDTO", optionalDTO);
            getBindingResultFromMapErrors(mapErrors, bindingResult, "optional");
            return "admin/updateOptionals";
        }
        return "redirect:/admin/optionals";
    }

}
