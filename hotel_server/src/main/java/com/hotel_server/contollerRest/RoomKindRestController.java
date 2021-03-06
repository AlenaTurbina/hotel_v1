package com.hotel_server.contollerRest;

import com.hotel_dto.dto.RoomKindDTO;
import com.hotel_dto.mapper.RoomKindMapper;
import com.hotel_server.service.RoomKindService;
import com.hotel_server.validator.RoomKindValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name="RoomKind", description = "Management of Room kind - getting list and creating")
public class RoomKindRestController {
    private RoomKindService roomKindService;
    private RoomKindMapper roomKindMapper;
    private RoomKindValidator roomKindValidator;

    @InitBinder(value = "roomKindDTO")
    void initRoomKindValidator(WebDataBinder binder) {
        binder.setValidator(roomKindValidator);
    }

    @Operation(summary = "Getting list of Room kinds")
    @GetMapping("/admin/roomKinds")
    List<RoomKindDTO> getAllRoomKinds() {
        return roomKindMapper.toListRoomKindDTO(roomKindService.getAllRoomKind());
    }

    @Operation(summary = "Creating new Room kind")
    @PostMapping("/admin/roomKinds")
    ResponseEntity createRoomKind(@RequestBody @Valid RoomKindDTO roomKindDTO) {
        return new ResponseEntity<>(roomKindMapper.toRoomKindDTO(roomKindService.saveRoomKind(roomKindDTO)), HttpStatus.CREATED);
    }

    @Operation(summary = "Getting list of Room kinds, if room with such Room kind is exist")
    @GetMapping("/uniqueRoomKindsFromRooms")
    List<RoomKindDTO> priceList() {
        return roomKindMapper.toListRoomKindDTO(roomKindService.getListUniqueRoomKindsFromRooms());
    }

}
