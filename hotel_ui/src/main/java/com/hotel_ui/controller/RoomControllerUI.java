package com.hotel_ui.controller;

import com.hotel_dto.dto.RoomDto;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static com.hotel_commons.error.Errors.getBindingResultFromMapErrors;


@Controller
@AllArgsConstructor
public class RoomControllerUI {

    private RestTemplate restTemplate;
    private static final String URL_HOST = Messages.getMessage("ui.url.host");
    private static final String URL_ROOM = URL_HOST + Messages.getMessage("ui.url.rooms");
    private static final String URL_UNIQUE_ROOM_KINDS = URL_HOST + Messages.getMessage("ui.url.uniqueRoomKinds");
    private static final String URL_CLASS_APARTMENTS = URL_HOST + Messages.getMessage("ui.url.classApartments");
    private static final String URL_ROOM_TYPES = URL_HOST + Messages.getMessage("ui.url.roomTypes");
    private static final String URL_ROOM_KINDS = URL_HOST + Messages.getMessage("ui.url.roomKinds");
    private static final String URL_OPTIONALS = URL_HOST + Messages.getMessage("ui.url.optionals");

    //List of roomKinds /GET/
    @GetMapping("/admin/rooms")
    public String getAllRooms(Model model) {
        List<RoomDto> roomDto = restTemplate.getForObject(URL_ROOM, List.class);
        model.addAttribute("rooms", roomDto);
        return "admin/rooms";
    }

    //Create optional /GET, POST/
    @GetMapping("/admin/rooms/create")
    private String createRoomForm(Model model) {
        model.addAttribute("roomDto", new RoomDto());
        showRoomTypeAndClassApartmentModelAndRoomKind(model);
        return "admin/createRooms";
    }

    @PostMapping("/admin/rooms/create")
    private String createRoom(@ModelAttribute RoomDto roomDTO, BindingResult bindingResult, Model model) {
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(URL_ROOM, roomDTO, Map.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            var mapErrors = responseEntity.getBody();
            getBindingResultFromMapErrors(mapErrors, bindingResult, "roomDto");
            showRoomTypeAndClassApartmentModelAndRoomKind(model);
            return "admin/createRooms";
        }
        return "redirect:/admin/rooms";
    }

    //Update optional /GET, POST/
    @GetMapping("/admin/rooms/update/{id}")
    public String updateRoomForm(@PathVariable("id") String id, Model model) {
        model.addAttribute("roomDto", restTemplate.getForObject((URL_ROOM + "/" + id), RoomDto.class));
        showRoomTypeAndClassApartmentModelAndRoomKind(model);
        return "admin/updateRooms";
    }


    @PostMapping("/admin/rooms/update")
    public String updateRoom(@ModelAttribute RoomDto roomDto, BindingResult bindingResult, Model model) {
        ResponseEntity<Map> responseEntity = restTemplate.exchange(URL_ROOM, HttpMethod.PUT, new HttpEntity<>(roomDto), Map.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            var mapErrors = responseEntity.getBody();
            getBindingResultFromMapErrors(mapErrors, bindingResult, "roomDto");
            showRoomTypeAndClassApartmentModelAndRoomKind(model);
            model.addAttribute("roomDto", roomDto);
            return "admin/updateRooms";
        }
        return "redirect:/admin/rooms";
    }

    //List of unique roomKinds from rooms and all optionals for price-list /GET/
    @GetMapping("/home/priceList")
    public String priceList(Model model) {
        model.addAttribute("roomKinds", restTemplate.getForObject(URL_UNIQUE_ROOM_KINDS, List.class));
        model.addAttribute("optionals", restTemplate.getForObject(URL_OPTIONALS, List.class));
        return "home/priceList";
    }

    //List of unique roomKinds from rooms for roomCards /GET/
    @GetMapping("/home/roomCards")
    public String showRoomCards(Model model) {
        model.addAttribute("roomKinds", restTemplate.getForObject(URL_UNIQUE_ROOM_KINDS, List.class));
        model.addAttribute("classApartments", restTemplate.getForObject(URL_CLASS_APARTMENTS, List.class));
        model.addAttribute("roomTypes", restTemplate.getForObject(URL_ROOM_TYPES, List.class));
        return "home/roomCards";
    }

    //Method for getting roomTypes and classApartments
    private void showRoomTypeAndClassApartmentModelAndRoomKind(Model model) {
        model.addAttribute("classApartments", restTemplate.getForObject(URL_CLASS_APARTMENTS, List.class));
        model.addAttribute("roomTypes", restTemplate.getForObject(URL_ROOM_TYPES, List.class));
        model.addAttribute("roomKinds", restTemplate.getForObject(URL_ROOM_KINDS, List.class));
    }
}

