package com.hotel_server.contollerRest;

import com.hotel_dto.dto.RoomKindDto;
import com.hotel_dto.mapper.RoomKindMapper;
import com.hotel_server.service.RoomKindService;
import com.hotel_server.validator.RoomKindUpdateValidator;
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
@Tag(name="Room Kind/Update", description = "Management of Room kind - update")
public class RoomKindUpdateRestController {
    private RoomKindService roomKindService;
    private RoomKindMapper roomKindMapper;
    private RoomKindUpdateValidator roomKindUpdateValidator;

    @InitBinder(value = "roomKindDTO")
    void initRoomKindUpdateValidator(WebDataBinder binder) {
        binder.setValidator(roomKindUpdateValidator);
    }

    @Operation(summary = "Getting Room kind by id")
    @GetMapping(value = "/admin/roomKinds/{id}")
    RoomKindDto getRoomKind(@PathVariable UUID id) {
        return roomKindMapper.toRoomKindDTO(roomKindService.getRoomKindById(id));
    }

    @Operation(summary = "Updating Room kind")
    @PutMapping("/admin/roomKinds")
    ResponseEntity updateRoomKind(@RequestBody @Valid RoomKindDto roomKindDTO) {
        return new ResponseEntity<>(roomKindMapper.toRoomKindDTO(roomKindService.updateRoomKind(roomKindDTO)), HttpStatus.CREATED);
    }

}
