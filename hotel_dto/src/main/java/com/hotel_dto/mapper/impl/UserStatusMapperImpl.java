package com.hotel_dto.mapper.impl;

import com.hotel_domain.model.entity.UserStatus;
import com.hotel_dto.dto.UserStatusDTO;
import com.hotel_dto.mapper.UserStatusMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserStatusMapperImpl implements UserStatusMapper {


    @Override
    public UserStatusDTO toUserStatusDTO(UserStatus userStatus) {
        UserStatusDTO userStatusDTO = new UserStatusDTO();
        userStatusDTO.setId(userStatus.getId());
        userStatusDTO.setName(userStatus.getName());
        return userStatusDTO;
    }

    @Override
    public List<UserStatusDTO> toListUserStatusDTO(List<UserStatus> userStatuses) {
        List<UserStatusDTO> userStatusesDTO = new ArrayList<>();
        for (UserStatus userStatus : userStatuses) {
            userStatusesDTO.add(toUserStatusDTO(userStatus));
        }
        return userStatusesDTO;
    }

}