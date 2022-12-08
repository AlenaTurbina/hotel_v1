package com.hotel_server.contollerRest;

import com.hotel_dto.dto.RoomDto;
import com.hotel_kafka.produser.ProducerSend;
import com.hotel_kafka.produser.ProducerSendString;
import com.hotel_server.validator.RoomValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api")
@ComponentScan(basePackages = {"com.hotel_kafka"})
@AllArgsConstructor
@Tag(name = "Room creating", description = "Management of Room - creating")
public class RoomManagerRestController {

    @InitBinder(value = "roomDTO")
    void initRoomValidator(WebDataBinder binder) {
        binder.setValidator(roomValidator);
    }

    private RoomValidator roomValidator;
    private ProducerSend producerSend;

//    //Test
//    private ProducerSendString producerSendString;

    @Operation(summary = "Creating new Room")
    @PostMapping("/admin/rooms")
    ResponseEntity createRoom(@RequestBody @Valid RoomDto roomDto) {
        producerSend.sendMessage(roomDto);
        System.out.println("Controller - message was sent");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    //Test
//    @PostMapping("/admin/String")
//    ResponseEntity create(@RequestBody String s) {
//        //    producerSend.sendToQueue(roomDto);
//        producerSendString.sendMessage(s);
//        System.out.println("Controller - message was sent");
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

}



