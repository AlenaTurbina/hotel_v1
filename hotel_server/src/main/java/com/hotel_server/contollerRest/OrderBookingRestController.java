package com.hotel_server.contollerRest;


import com.hotel_domain.model.entity.OrderBooking;
import com.hotel_dto.dto.OrderBookingDTO;
import com.hotel_dto.dto.UserDTO;
import com.hotel_dto.mapper.OrderBookingMapper;
import com.hotel_dto.mapper.RoomMapper;
import com.hotel_server.message.Messages;
import com.hotel_server.service.OrderBookingService;
import com.hotel_server.service.impl.EmailSenderServiceImpl;
import com.hotel_server.validator.OrderBookingValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Slf4j
@Tag(name="OrderBooking", description = "Management of Order booking - getting list and creating")
public class OrderBookingRestController {
    private OrderBookingService orderBookingService;
    private OrderBookingMapper orderBookingMapper;
    private RoomMapper roomMapper;
    private EmailSenderServiceImpl emailSenderService;
    private OrderBookingValidator orderBookingValidator;

    private static final String EMAIL_BOOKING_SUBJECT = Messages.getMessage("server.booking.emailBookingSubject");
    private static final String EMAIL_BOOKING_TEST = Messages.getMessage("server.booking.testEmail");

    @InitBinder(value = "orderBookingDTO")
    void initOrderBookingValidator(WebDataBinder binder) {
        binder.setValidator(orderBookingValidator);
    }

    @Operation(summary = "Getting list of Order bookings")
    @GetMapping("/admin/orderBookings")
    List<OrderBookingDTO> getAllOrders() {
        return orderBookingMapper.toListOrderBookingDTO(orderBookingService.getAllOrderBooking());
    }

    @Operation(summary = "Creating new Order booking")
    @PostMapping("/orderBooking/create")
    ResponseEntity registerOrderBooking(@RequestBody @Valid OrderBookingDTO orderBookingDTO) {
        OrderBooking orderBooking = orderBookingService.saveOrderBooking(orderBookingDTO);
        if (orderBooking != null) {
            log.info("New invoice");
            try {
//                for test reason the user.email  == EMAIL_BOOKING_TEST
//                emailSenderService.sendHtmlEmailInvoice(orderBooking.getClient().getEmail(), EMAIL_BOOKING_SUBJECT, orderBooking, "/mails/mailInvoice.html");
                emailSenderService.sendHtmlEmailInvoice(EMAIL_BOOKING_TEST, EMAIL_BOOKING_SUBJECT, orderBooking, "/mails/mailInvoice.html");
                log.info("New email was sent");
            } catch (MessagingException e) {
                log.error("Exception: ", e);
            }
            return new ResponseEntity<>(orderBookingMapper.toOrderBookingDTO(orderBooking), HttpStatus.CREATED);
        } else {
            log.info("No suitable room for booking");
            return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
        }
    }

    @Operation(summary = "Getting List of Order bookings by user")
    @PostMapping("/orderBooking/user")
    List<OrderBookingDTO> getOrderBookingForUser(@RequestBody UserDTO userDTO) {
        return orderBookingMapper.toListOrderBookingDTO(orderBookingService.findAllOrderBookingsByUser(userDTO));
    }

    @Operation(summary = "Getting Order booking by id")
    @GetMapping(value = "/admin/orderBookings/{id}")
    OrderBookingDTO getOrderBooking(@PathVariable UUID id) {
        return orderBookingMapper.toOrderBookingDTO(orderBookingService.getOrderBookingById(id));
    }

    @Operation(summary = "Updating Order booking")
    @PutMapping("/admin/orderBookings")
    ResponseEntity updateOrderBooking(@RequestBody OrderBookingDTO orderBookingDTO) {
        return new ResponseEntity<>(orderBookingMapper
                .toOrderBookingDTO(orderBookingService.updateOrderBooking(orderBookingDTO)), HttpStatus.CREATED);
    }

    @Operation(summary = "Getting list of free rooms to certain Order booking")
    @PostMapping("/roomsForDates")
    ResponseEntity getRoomsOnOrderBookingDate(@RequestBody UUID id) {
        var rooms = orderBookingService.getListFreeRoomsOnOrderBookingDates(orderBookingService.getOrderBookingById(id));
        return new ResponseEntity<>(roomMapper.toListRoomDTO(rooms), HttpStatus.OK);
    }

}
