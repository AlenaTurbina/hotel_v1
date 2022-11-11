package com.hotel_server.service.impl;

import com.hotel_database.model.repository.UserStatusRepository;
import com.hotel_domain.model.entity.UserStatus;
import com.hotel_server.exceptionHandler.exception.ServerEntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserStatusServiceImplTest {
    @Mock
    private UserStatusRepository userStatusRepository;
    @InjectMocks
    private UserStatusServiceImpl userStatusService;

    private UserStatus userStatus;

//    @BeforeEach
//    public void setUp() {
//        userStatus = UserStatus.builder()
//                .id(1)
//                .name("US1")
//                .build();
//    }
//
//    @DisplayName("JUnit test for getAllUserStatuses method")
//    @Test
//    void test_WhenGetAll_ThenReturnUserStatusList() {
//        UserStatus userStatus1 = UserStatus.builder()
//                .id(2)
//                .name("US2")
//                .build();
//        given(userStatusRepository.findAll()).willReturn(List.of(userStatus, userStatus1));
//        List<UserStatus> userStatusList = userStatusService.getAllUserStatuses();
//
//        assertThat(userStatusList).isNotEmpty();
//        assertThat(userStatusList.size()).isEqualTo(2);
//    }
//
//    @DisplayName("JUnit test for getAllUserStatuses method (empty list)")
//    @Test
//    void test_WhenGetAll_ThenReturnEmptyUserStatusList() {
//        UserStatus userStatus1 = UserStatus.builder()
//                .id(2)
//                .name("US2")
//                .build();
//
//        given(userStatusRepository.findAll()).willReturn(Collections.emptyList());
//        List<UserStatus> userStatusList = userStatusService.getAllUserStatuses();
//
//        assertThat(userStatusList).isEmpty();
//        assertThat(userStatusList.size()).isEqualTo(0);
//    }
//
//    @DisplayName("JUnit test for getUserStatusById method")
//    @Test
//    void test_GivenUserStatusId_WhenGetUserStatusById_thenReturnUserStatus() {
//        given(userStatusRepository.findById(userStatus.getId()))
//                .willReturn(Optional.of(userStatus));
//        UserStatus userStatusExpected = userStatusService.getUserStatusById(userStatus.getId());
//
//        assertThat(userStatusExpected).isNotNull();
//    }
//
//    @DisplayName("JUnit test for getUserStatusById method (throw exception)")
//    @Test
//    void test_GivenUserStatusId_WhenGetUserStatusById_ThenThrowException() {
//        given(userStatusRepository.findById(userStatus.getId()))
//                .willReturn(Optional.empty());
//
//        Assertions.assertThrows(ServerEntityNotFoundException.class,
//                () -> userStatusService.getUserStatusById(userStatus.getId()));
//    }

}