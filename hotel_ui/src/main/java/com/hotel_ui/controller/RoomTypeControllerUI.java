package com.hotel_ui.controller;

import com.hotel_dto.dto.RoomTypeDTO;
import com.hotel_ui.message.Messages;
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


@RequestMapping("/admin/roomTypes")
@Controller
@AllArgsConstructor
public class RoomTypeControllerUI {

    private RestTemplate restTemplate;

    private static final String URL_HOST = Messages.getMessage("ui.url.host");
    private static final String URL_ROOM_TYPES = URL_HOST + Messages.getMessage("ui.url.roomTypes");


    //List of Optionals /GET/
    @GetMapping()
    private String roomTypes(Model model) {
        model.addAttribute("roomTypes", restTemplate.getForObject(URL_ROOM_TYPES, List.class));
        return "admin/roomTypes";
    }

    //Create optional /GET, POST/
    @GetMapping("/create")
    private String createRoomTypesForm(Model model) {
        model.addAttribute("roomTypeDTO", new RoomTypeDTO());
        return "admin/createRoomTypes";
    }

    @PostMapping("/create")
    private String createRoomType(@ModelAttribute RoomTypeDTO roomTypeDTO, BindingResult bindingResult) {
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(URL_ROOM_TYPES, roomTypeDTO, Map.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            var mapErrors = responseEntity.getBody();
            getBindingResultFromMapErrors(mapErrors, bindingResult, "roomTypeDTO");
            return "admin/createRoomTypes";
        }
        return "redirect:/admin/roomTypes";
    }

    //Update optional /GET, POST/
    @GetMapping("/update/{id}")
    public String updateRoomTypesForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("roomTypeDTO", restTemplate.getForObject((URL_ROOM_TYPES + "/" + id), RoomTypeDTO.class));
        return "admin/updateRoomTypes";
    }

    @PostMapping("/update")
    public String updateRoomTypes(@ModelAttribute RoomTypeDTO roomTypeDTO, BindingResult bindingResult, Model model) {
        ResponseEntity<Map> responseEntity = restTemplate.exchange(URL_ROOM_TYPES, HttpMethod.PUT, new HttpEntity<>(roomTypeDTO), Map.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            var mapErrors = responseEntity.getBody();
            getBindingResultFromMapErrors(mapErrors, bindingResult, "optional");
            model.addAttribute("roomTypeDTO", roomTypeDTO);
            return "admin/updateRoomTypes";
        }
        return "redirect:/admin/roomTypes";
    }

}
