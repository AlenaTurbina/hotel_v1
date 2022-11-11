package com.hotel_server.service.impl;

import com.hotel_database.model.repository.UserRepository;
import com.hotel_domain.model.entity.Role;
import com.hotel_domain.model.entity.User;
import com.hotel_domain.model.entity.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private User user1;

    @BeforeEach
    public void setUp() {
        UserStatus userStatus = UserStatus.builder()
                .id(UUID.randomUUID())
                .name("US1")
                .build();
        Role role = Role.builder()
                .id(UUID.randomUUID())
                .name("R1")
                .build();
        user = User.builder()
                .id(UUID.randomUUID())
                .email("user@test.com")
                .firstName("firstName")
                .lastName("lastName")
                .document("AA")
                .phoneNumber("+123456789")
                .password("111")
                .userStatus(userStatus)
                .orderBookings(null)
                .roles(List.of(role))
                .build();
        UserStatus userStatus1 = UserStatus.builder()
                .id(UUID.randomUUID())
                .name("US2")
                .build();
        Role role1 = Role.builder()
                .id(UUID.randomUUID())
                .name("R2")
                .build();
        user1 = User.builder()
                .id(UUID.randomUUID())
                .email("user1@test.com")
                .firstName("firstName1")
                .lastName("lastName1")
                .document("AA1")
                .phoneNumber("+123456789")
                .password("222")
                .userStatus(userStatus1)
                .orderBookings(null)
                .roles(List.of(role1))
                .build();
    }

    @DisplayName("JUnit test for getAllUsers method")
    @Test
    void test_WhenGetAllUsers_ThenReturnUserList() {
        given(userRepository.findAll()).willReturn(List.of(user, user1));
        List<User> userList = userService.getAllUsers();

        assertThat(userList).isNotEmpty();
        assertThat(userList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getAllUsers method (empty list)")
    @Test
    void WhenGetAllUsers_ThenReturnEmptyUserList() {
        given(userRepository.findAll()).willReturn(Collections.emptyList());
        List<User> userList = userService.getAllUsers();

        assertThat(userList).isEmpty();
        assertThat(userList.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getUserByEmail method")
    @Test
    void test_GivenUserEmail_WhenFindUserByEmail_ThenReturnUser() {
        given(userRepository.findByEmail(user.getEmail())).willReturn(user);
        User userExpected = userService.getUserByEmail(user.getEmail());

        assertThat(userExpected).isNotNull();
    }

    @DisplayName("JUnit test for getUserByEmail method (return null)")
    @Test
    void test_GivenUserEmail_WhenFindUserByEmail_ThenReturnNull() {
        given(userRepository.findByEmail(user.getEmail())).willReturn(null);
        User userExpected = userService.getUserByEmail(user.getEmail());

        assertThat(userExpected).isNull();
    }
}