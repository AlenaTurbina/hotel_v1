package com.hotel_server.contollerRest;

import com.hotel_dto.dto.RoomTypeDTO;
import com.hotel_dto.mapper.RoomTypeMapper;
import com.hotel_server.service.RoomTypeService;
import com.hotel_server.validator.RoomTypeValidator;
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
@Tag(name="RoomType", description = "Management of Room type - getting list and creating")
public class RoomTypeRestController {
    private RoomTypeService roomTypeService;
    private RoomTypeMapper roomTypeMapper;
    private RoomTypeValidator roomTypeValidator;

    @InitBinder(value = "roomTypeDTO")
    void initRoomTypeValidator(WebDataBinder binder) {
        binder.setValidator(roomTypeValidator);
    }

    @Operation(summary = "Getting list of Room types")
    @GetMapping("/admin/roomTypes")
    List<RoomTypeDTO> getAllRoomTypes() {
        return roomTypeMapper.toListRoomTypeDTO(roomTypeService.getAll());
    }

    @Operation(summary = "Creating new Room type")
    @PostMapping("/admin/roomTypes")
    ResponseEntity createRoomType(@RequestBody @Valid RoomTypeDTO roomTypeDTO) {
        return new ResponseEntity<>(roomTypeMapper.toRoomTypeDTO(roomTypeService.saveRoomType(roomTypeDTO)), HttpStatus.CREATED);
    }

    @Operation(summary = "Getting list of Room types, if room with such Room type is exist")
    @GetMapping("/uniqueRoomTypesFromRooms")
    List<RoomTypeDTO> showUniqueRoomTypesFromRooms() {
        return roomTypeMapper.toListRoomTypeDTO(roomTypeService.getListUniqueRoomTypesFromRooms());
    }
}
