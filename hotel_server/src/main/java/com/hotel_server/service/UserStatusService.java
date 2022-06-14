package com.hotel_server.service;

import com.hotel_domain.model.entity.UserStatus;

import java.util.List;

public interface UserStatusService {
    List<UserStatus> getAllUserStatuses();

    UserStatus getUserStatusById(Integer id);
}
