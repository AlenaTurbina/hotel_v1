package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.UserStatus;
import com.hotel_dto.dto.UserStatusDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserStatusMapper {
    UserStatusDto toUserStatusDto(UserStatus userStatus);

    List<UserStatusDto> toListUserStatusDto(List<UserStatus> userStatuses);
}
