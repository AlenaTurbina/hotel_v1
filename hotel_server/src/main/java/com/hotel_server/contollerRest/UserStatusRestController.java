package com.hotel_server.contollerRest;

import com.hotel_dto.dto.UserStatusDto;
import com.hotel_dto.mapper.UserStatusMapper;
import com.hotel_server.service.UserStatusService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
@Tag(name="UserStatus", description = "Management of User status - getting list")
public class UserStatusRestController {
    private UserStatusService userStatusService;
    private UserStatusMapper userStatusMapper;

    @Operation(summary = "Getting list of User statuses")
    @GetMapping("/admin/userStatuses")
    List<UserStatusDto> getAllUserStatuses() {
        return userStatusMapper.toListUserStatusDto(userStatusService.getAllUserStatuses());
    }

}
