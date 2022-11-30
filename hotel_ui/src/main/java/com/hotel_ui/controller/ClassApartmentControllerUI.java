package com.hotel_ui.controller;

import com.hotel_ui.message.Messages;
import com.hotel_dto.dto.ClassApartmentDto;
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


@RequestMapping("/admin/classApartments")
@Controller
@AllArgsConstructor
public class ClassApartmentControllerUI {

    private RestTemplate restTemplate;
    private static final String URL_HOST = Messages.getMessage("ui.url.host");
    private static final String URL_CLASS_APARTMENTS = URL_HOST + Messages.getMessage("ui.url.classApartments");

    //List of ClassApartments /GET/
    @GetMapping()
    private String classApartments(Model model) {
        List<ClassApartmentDto> classApartments = restTemplate.getForObject(URL_CLASS_APARTMENTS, List.class);
        model.addAttribute("classApartments", classApartments);
        return "admin/classApartments";
    }

    //Create classApartment /GET, POST/
    @GetMapping("/create")
    private String createClassApartmentForm(Model model) {
        model.addAttribute("classApartmentDto", new ClassApartmentDto());
        return "admin/createClassApartments";
    }

    @PostMapping("/create")
    private String createClassApartment(@ModelAttribute ClassApartmentDto classApartmentDto, BindingResult bindingResult) {
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(URL_CLASS_APARTMENTS, classApartmentDto, Map.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            var mapErrors = responseEntity.getBody();
            getBindingResultFromMapErrors(mapErrors, bindingResult, "classApartmentDto");
            return "admin/createClassApartments";
        }
        return "redirect:/admin/classApartments";
    }

    //Update classApartment /GET, POST/
    @GetMapping("/update/{id}")
    public String updateClassApartmentForm(@PathVariable("id") String id, Model model) {
        ClassApartmentDto classApartmentDto = restTemplate.getForObject((URL_CLASS_APARTMENTS + "/" + id), ClassApartmentDto.class);
        model.addAttribute("classApartmentDto", classApartmentDto);
        return "admin/updateClassApartments";
    }

    @PostMapping("/update")
    public String updateClassApartments(@ModelAttribute ClassApartmentDto classApartmentDto, BindingResult bindingResult, Model model) {
        HttpEntity<ClassApartmentDto> httpEntity = new HttpEntity<ClassApartmentDto>(classApartmentDto);
        ResponseEntity<Map> responseEntity = restTemplate.exchange(URL_CLASS_APARTMENTS, HttpMethod.PUT, httpEntity, Map.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            var mapErrors = responseEntity.getBody();
            getBindingResultFromMapErrors(mapErrors, bindingResult, "classApartment");
            model.addAttribute("classApartmentDto", classApartmentDto);
            return "admin/updateClassApartments";
        }
        return "redirect:/admin/classApartments";
    }

}
