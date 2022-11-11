package com.hotel_server.service.impl;

import com.hotel_database.model.repository.UserStatusRepository;
import com.hotel_domain.model.entity.UserStatus;
import com.hotel_server.exceptionHandler.exception.ServerEntityNotFoundException;
import com.hotel_server.service.UserStatusService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class UserStatusServiceImpl implements UserStatusService {
    private UserStatusRepository userStatusRepository;

    @Override
    public List<UserStatus> getAllUserStatuses() {
        log.info("UserStatus getAll");
        return userStatusRepository.findAll();
    }

    @Override
    public UserStatus getUserStatusById(UUID id) {
        log.info("Get user status by id " + id);
        return userStatusRepository.findById(id).orElseThrow(() -> new ServerEntityNotFoundException(id));
    }
}
