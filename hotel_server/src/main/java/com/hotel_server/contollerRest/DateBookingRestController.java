package com.hotel_server.contollerRest;

import com.hotel_dto.dto.OrderBookingDto;
import com.hotel_server.service.OrderBookingService;
import com.hotel_server.validator.DateBookingValidator;
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
@Tag(name = "Free rooms", description = "Actual information about free rooms")
public class DateBookingRestController {
    private OrderBookingService orderBookingService;
    private DateBookingValidator dateBookingValidator;


    @InitBinder(value = "orderBookingDTO")
    void initDateBookingValidator(WebDataBinder binder) {
        binder.setValidator(dateBookingValidator);
    }

    @Operation(summary = "Getting list of RoomKinds with quantity of free rooms")
    @PostMapping("/home/freeRooms")
    ResponseEntity getRoomKindsWithFreeRoomsQuantity(@RequestBody @Valid OrderBookingDto orderBookingDTO) {
        return new ResponseEntity<>(orderBookingService.getRoomKindsWithFreeRooms(orderBookingDTO), HttpStatus.CREATED);
    }

}
