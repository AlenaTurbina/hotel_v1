package com.hotel_server.contollerRest;

import com.hotel_dto.dto.RoomDTO;
import com.hotel_dto.mapper.ClassApartmentMapper;
import com.hotel_dto.mapper.RoomKindMapper;
import com.hotel_dto.mapper.RoomMapper;
import com.hotel_dto.mapper.RoomTypeMapper;
import com.hotel_server.service.RoomService;
import com.hotel_server.validator.RoomValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name="Room", description = "Management of Room - getting lists")
public class RoomRestController {
    private RoomService roomService;
    private RoomMapper roomMapper;
    private RoomKindMapper roomKindMapper;
    private RoomTypeMapper roomTypeMapper;
    private ClassApartmentMapper classApartmentMapper;
    private RoomValidator roomValidator;

    @InitBinder(value = "roomDTO")
    void initRoomValidator(WebDataBinder binder) {
        binder.setValidator(roomValidator);
    }

    @Operation(summary = "Getting list of Rooms")
    @GetMapping("/admin/rooms")
    List<RoomDTO> getAllRooms() {
        return roomMapper.toListRoomDTO(roomService.getAllRooms());
    }

}
