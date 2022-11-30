package com.hotel_server.contollerRest;

import com.hotel_domain.model.entity.RoomType;
import com.hotel_dto.dto.RoomTypeDto;
import com.hotel_dto.mapper.RoomTypeMapper;
import com.hotel_server.service.RoomTypeService;
import com.hotel_server.validator.RoomTypeUpdateValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@RequestMapping("/api")
@RestController
@AllArgsConstructor
@Tag(name="Room Type/Update", description = "Management of Room type - update")
public class RoomTypeUpdateRestController {
    private RoomTypeService roomTypeService;
    private RoomTypeMapper roomTypeMapper;
    private RoomTypeUpdateValidator roomTypeUpdateValidator;

    @InitBinder(value = "roomTypeDTO")
    void initRoomTypeUpdateValidator(WebDataBinder binder) {
        binder.setValidator(roomTypeUpdateValidator);
    }

    @Operation(summary = "Getting Room type by id")
    @GetMapping(value = "/admin/roomTypes/{id}")
    RoomTypeDto getRoomType(@PathVariable UUID id) {
        return roomTypeMapper.toRoomTypeDto(roomTypeService.getRoomTypeById(id));
    }

    @Operation(summary = "Updating Room type")
    @PutMapping("/admin/roomTypes")
    ResponseEntity updateRoomType(@RequestBody @Valid RoomTypeDto roomTypeDTO) {
        RoomType roomType = roomTypeMapper.toRoomType(roomTypeDTO);
        return new ResponseEntity<>(roomTypeMapper.toRoomTypeDto(roomTypeService.updateRoomType(roomType)), HttpStatus.CREATED);
    }

}
