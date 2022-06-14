package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.UserStatus;
import com.hotel_dto.dto.UserStatusDTO;

import java.util.List;

public interface UserStatusMapper {
    UserStatusDTO toUserStatusDTO(UserStatus userStatus);

    List<UserStatusDTO> toListUserStatusDTO(List<UserStatus> userStatuses);

}
