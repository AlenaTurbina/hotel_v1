package com.hotel_ui.controller;

import com.hotel_dto.dto.RoomKindDTO;
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

@RequestMapping("/admin/roomKinds")
@Controller
@AllArgsConstructor
public class RoomKindControllerUI {

    private RestTemplate restTemplate;
    private static final String URL_HOST = Messages.getMessage("ui.url.host");
    private static final String URL_CLASS_APARTMENTS = URL_HOST + Messages.getMessage("ui.url.classApartments");
    private static final String URL_ROOM_TYPES = URL_HOST + Messages.getMessage("ui.url.roomTypes");
    private static final String URL_ROOM_KINDS = URL_HOST + Messages.getMessage("ui.url.roomKinds");


    @GetMapping()
    public String roomKinds(Model model) {
        model.addAttribute("roomKinds", restTemplate.getForObject(URL_ROOM_KINDS, List.class));
        return "admin/roomKinds";
    }

    @GetMapping("/create")
    private String createRoomKindForm(Model model) {
        model.addAttribute("roomKindDTO", new RoomKindDTO());
        showRoomTypeAndClassApartmentModel(model);
        return "admin/createRoomKinds";
    }

    @PostMapping("/create")
    private String createRoomKind(@ModelAttribute RoomKindDTO roomKindDTO, BindingResult bindingResult, Model model) {
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(URL_ROOM_KINDS, roomKindDTO, Map.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            var mapErrors = responseEntity.getBody();
            getBindingResultFromMapErrors(mapErrors, bindingResult, "roomKindDTO");
            showRoomTypeAndClassApartmentModel(model);
            return "admin/createRoomKinds";
        }
        return "redirect:/admin/roomKinds";
    }

    @GetMapping("/update/{id}")
    public String updateRoomKindForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("roomKindDTO", restTemplate.getForObject((URL_ROOM_KINDS + "/" + id), RoomKindDTO.class));
        showRoomTypeAndClassApartmentModel(model);
        return "admin/updateRoomKinds";
    }

    @PostMapping("/update")
    public String updateRoomKind(@ModelAttribute RoomKindDTO roomKindDTO, BindingResult bindingResult, Model model) {
        ResponseEntity<Map> responseEntity = restTemplate.exchange(URL_ROOM_KINDS, HttpMethod.PUT, new HttpEntity<>(roomKindDTO), Map.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            var mapErrors = responseEntity.getBody();
            getBindingResultFromMapErrors(mapErrors, bindingResult, "roomKind");
            showRoomTypeAndClassApartmentModel(model);
            model.addAttribute("roomKindDTO", roomKindDTO);
            return "admin/updateRoomKinds";
        }
        return "redirect:/admin/roomKinds";
    }

    //Method for getting roomTypes and classApartments
    private void showRoomTypeAndClassApartmentModel(Model model) {
        model.addAttribute("classApartments", restTemplate.getForObject(URL_CLASS_APARTMENTS, List.class));
        model.addAttribute("roomTypes", restTemplate.getForObject(URL_ROOM_TYPES, List.class));
    }

}

