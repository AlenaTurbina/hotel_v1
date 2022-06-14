package com.hotel_ui.controller;

import com.hotel_dto.dto.OrderBookingDTO;
import com.hotel_dto.dto.RoomKindDTO;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static com.hotel_commons.error.Errors.getBindingResultFromMapErrors;
import static com.hotel_ui.security.UserPrincipal.getPrincipal;


@Controller
@AllArgsConstructor
public class OrderBookingControllerUI {

    private RestTemplate restTemplate;
    private static final String URL_HOST = Messages.getMessage("ui.url.host");
    private static final String URL_ORDER_BOOKINGS = URL_HOST + Messages.getMessage("ui.url.orderBookings");
    private static final String URL_FREE_ROOMS = URL_HOST + Messages.getMessage("ui.url.freeRooms");
    private static final String URL_UNIQUE_ROOM_KINDS = URL_HOST + Messages.getMessage("ui.url.uniqueRoomKinds");
    private static final String URL_ORDER_BOOKING_CREATE = URL_HOST + Messages.getMessage("ui.url.orderBookingCreate");
    private static final String URL_ORDER_BOOKING_USER = URL_HOST + Messages.getMessage("ui.url.orderBookingUser");
    private static final String URL_ORDER_BOOKING_ROOMS = URL_HOST + Messages.getMessage("ui.url.orderBookingRooms");
    private static final String URL_ORDER_STATUSES = URL_HOST + Messages.getMessage("ui.url.orderStatuses");
    private static final String URL_UNIQUE_ROOM_TYPES = URL_HOST + Messages.getMessage("ui.url.uniqueRoomTypes");
    private static final String URL_UNIQUE_CLASS_APARTMENTS = URL_HOST + Messages.getMessage("ui.url.uniqueClassApartments");
    private static final String URL_OPTIONALS = URL_HOST + Messages.getMessage("ui.url.optionals");

    //List of roomKinds /GET/
    @GetMapping("/admin/orderBookings")
    public String getAllOrderBookings(Model model) {
        List<OrderBookingDTO> orderBookingsDTO = restTemplate.getForObject(URL_ORDER_BOOKINGS, List.class);
        model.addAttribute("orderBookings", orderBookingsDTO);
        return "admin/orderBookings";
    }

    //Getting list of free rooms on selected dates for clients /GET, POST/
    @GetMapping(value = "/home/freeRoomForms")
    public String freeRoomsForm(Model model) {
        model.addAttribute("orderBookingDTO", new OrderBookingDTO());
        return "home/freeRoomForms";
    }

    //Getting list of free rooms on selected dates for admins /GET, POST/
    @GetMapping(value = "/admin/freeRoomFormsAdmin")
    public String freeRoomsFormAdmin(Model model) {
        model.addAttribute("orderBookingDTO", new OrderBookingDTO());
        return "admin/freeRoomFormsAdmin";
    }

    @PostMapping("/home/freeRoomForms")
    private String freeRooms(@ModelAttribute OrderBookingDTO orderBookingDTO, BindingResult bindingResult, Model model) {
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(URL_FREE_ROOMS, orderBookingDTO, Map.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            var mapErrors = responseEntity.getBody();
            getBindingResultFromMapErrors(mapErrors, bindingResult, "orderBookingDTO");
            return "home/freeRoomForms";
        }
        model.addAttribute("freeRooms", responseEntity.getBody());
        model.addAttribute("roomKinds", restTemplate.getForObject(URL_UNIQUE_ROOM_KINDS, List.class));
        model.addAttribute("orderBookingDTO", orderBookingDTO);
        return "home/freeRoomsClient";
    }

    @PostMapping("/admin/freeRoomFormsAdmin")
    private String freeRoomsAdmin(@ModelAttribute OrderBookingDTO orderBookingDTO, BindingResult bindingResult, Model model) {
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(URL_FREE_ROOMS, orderBookingDTO, Map.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            var mapErrors = responseEntity.getBody();
            getBindingResultFromMapErrors(mapErrors, bindingResult, "orderBookingDTO");
            return "admin/freeRoomFormsAdmin";
        }
        Map<RoomKindDTO, Integer> freeRoomsMap = responseEntity.getBody();
        model.addAttribute("freeRooms", freeRoomsMap);
        model.addAttribute("roomKinds", restTemplate.getForObject(URL_UNIQUE_ROOM_KINDS, List.class));
        model.addAttribute("orderBookingDTO", orderBookingDTO);
        return "admin/freeRoomsAdmin";
    }

    //BOOKING
    @GetMapping(value = "/client/orderForms")
    public String orderForm(Model model) {
        model.addAttribute("orderBookingDTO", new OrderBookingDTO());
        showAuthenticatedUserAndOrderForm(model);
        return "client/orderForms";
    }

    @PostMapping("/client/orderForms")
    private String createOrderBooking(@ModelAttribute OrderBookingDTO orderBookingDTO, BindingResult bindingResult, Model model) {
        ResponseEntity<Map> responseEntity = restTemplate.postForEntity(URL_ORDER_BOOKING_CREATE, orderBookingDTO, Map.class);
        showAuthenticatedUserAndOrderForm(model);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            var mapErrors = responseEntity.getBody();
            getBindingResultFromMapErrors(mapErrors, bindingResult, "orderBookingDTO");
            return "client/orderForms";
        }
        if (responseEntity.getStatusCode() == HttpStatus.RESET_CONTENT) {
            model.addAttribute("orderBookingDTO", orderBookingDTO);
            return "client/orderResultRefusal";
        } else {
            Integer id = (Integer) responseEntity.getBody().get("id");
            OrderBookingDTO orderBookingDTObyId = restTemplate.getForObject((URL_ORDER_BOOKINGS + "/" + id), OrderBookingDTO.class);
            model.addAttribute("orderBooking", orderBookingDTObyId);
            return "client/orderResultInvoice";
        }
    }

    @GetMapping("/client/orderBookings")
    public String orderBookingsForUser(Model model) {
        String userEmail = getPrincipal();
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(userEmail);
        ResponseEntity<List> responseEntityForUser = restTemplate.postForEntity(URL_ORDER_BOOKING_USER, userDTO, List.class);
        List<OrderBookingDTO> orderBookingsDTO = responseEntityForUser.getBody();
        model.addAttribute("orderBookings", orderBookingsDTO);
        showAuthenticatedUserAndOrderForm(model);
        return "client/orderUsers";
    }

    //Update orderBooking /GET, POST/
    @GetMapping("/admin/orderBookings/update/{id}")
    public String updateOrderBookingForm(@PathVariable("id") Integer id, Model model) {
        OrderBookingDTO orderBookingDTO = restTemplate.getForObject((URL_ORDER_BOOKINGS + "/" + id), OrderBookingDTO.class);
        model.addAttribute("orderBookingDTO", orderBookingDTO);
        ResponseEntity<List> responseEntityRooms = restTemplate.postForEntity(URL_ORDER_BOOKING_ROOMS, id, List.class);
        model.addAttribute("rooms", responseEntityRooms.getBody());
        model.addAttribute("orderStatuses", restTemplate.getForObject(URL_ORDER_STATUSES, List.class));
        return "admin/updateOrderBookings";
    }

    @PostMapping("/admin/orderBookings/update")
    public String updateOrderBooking(@ModelAttribute OrderBookingDTO orderBookingDTO, BindingResult bindingResult, Model model) {
        ResponseEntity<Map> responseEntity = restTemplate.exchange(URL_ORDER_BOOKINGS, HttpMethod.PUT, new HttpEntity<>(orderBookingDTO), Map.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            var mapErrors = responseEntity.getBody();
            getBindingResultFromMapErrors(mapErrors, bindingResult, "orderBookingDTO");
            return "admin/updateOrderBookings";
        }
        return "redirect:/admin/orderBookings";
    }


    //Method for getting list of authenticated client & orderBooking form
    private void showAuthenticatedUserAndOrderForm(Model model) {
        model.addAttribute("userEmail", getPrincipal());
        model.addAttribute("classApartmentDTOList", restTemplate.getForObject(URL_UNIQUE_CLASS_APARTMENTS, List.class));
        model.addAttribute("roomTypeDTOList", restTemplate.getForObject(URL_UNIQUE_ROOM_TYPES, List.class));
        model.addAttribute("optionals", restTemplate.getForObject(URL_OPTIONALS, List.class));
    }
}

