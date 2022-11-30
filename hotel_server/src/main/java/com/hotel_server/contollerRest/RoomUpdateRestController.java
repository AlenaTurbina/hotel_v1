package com.hotel_server.contollerRest;

import com.hotel_dto.dto.RoomDto;
import com.hotel_dto.mapper.RoomMapper;
import com.hotel_server.service.RoomService;
import com.hotel_server.validator.RoomUpdateValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name="Room/Update", description = "Management of Room - update")
public class RoomUpdateRestController {
    private RoomService roomService;
    private RoomMapper roomMapper;
    private RoomUpdateValidator roomUpdateValidator;

    @InitBinder(value = "roomDTO")
    void initRoomUpdateValidator(WebDataBinder binder) {
        binder.setValidator(roomUpdateValidator);
    }

    @Operation(summary = "Getting Room by id")
    @GetMapping(value = "/admin/rooms/{id}")
    RoomDto getRoom(@PathVariable UUID id) {
        return roomMapper.toRoomDTO(roomService.getRoomById(id));
    }

    @Operation(summary = "Updating Room")
    @PutMapping("/admin/rooms")
    ResponseEntity updateRoom(@RequestBody @Valid RoomDto roomDTO) {
        return new ResponseEntity<>(roomMapper.toRoomDTO(roomService.updateRoom(roomDTO)), HttpStatus.CREATED);
    }

}
