package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.OrderBooking;
import com.hotel_domain.model.entity.Role;
import com.hotel_domain.model.entity.User;
import com.hotel_domain.model.entity.UserStatus;
import com.hotel_dto.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ComponentScan("com.hotel_domain")
public class UserMapperTest {
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);
    User user;
    Role role;
    UserStatus userStatus;
    public static final String id1 = "788c07d7-4275-4101-8fc3-1d77986f2129";
    public static final String id2 = "888c07d7-4275-4101-8fc3-1d77986f2129";
    public static final String id3 = "988c07d7-4275-4101-8fc3-1d77986f2129";

    @BeforeEach
    void setUp() {
        userStatus = new UserStatus(UUID.fromString(id1), "A");
        role = new Role(UUID.fromString(id1), "B");
        List<OrderBooking> emptyList = Collections.emptyList();
        user = new User(UUID.fromString(id1), "user@user.com", "123",
                "A", "B", "+123123123", "AA123",
                userStatus, emptyList, new ArrayList<>(List.of(role))
        );
    }

    @Test
    public void givenToUserDto_whenMapper_ThenCorrect() {
        UserDto userDto = userMapper.toUserDTO(user);

        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getPhoneNumber(), userDto.getPhoneNumber());
        assertEquals(user.getDocument(), userDto.getDocument());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getUserStatus().getId(), userDto.getUserStatusId());
        assertEquals(user.getUserStatus().getName(), userDto.getUserStatusName());
        assertEquals(user.getPassword(), userDto.getPassword());
        assertEquals(List.of(role.getName()), userDto.getRolesName());
    }

    @Test
    public void givenToUserDtoAuth_whenMapper_ThenCorrect() {
        UserDto userDto = userMapper.toUserDtoAuth(user);

        assertEquals(null, userDto.getId());
        assertEquals(null, userDto.getFirstName());
        assertEquals(null, userDto.getLastName());
        assertEquals(null, userDto.getPhoneNumber());
        assertEquals(null, userDto.getDocument());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertEquals(user.getUserStatus().getId(), userDto.getUserStatusId());
        assertEquals(null, userDto.getUserStatusName());
        assertEquals(user.getPassword(), userDto.getPassword());
        assertEquals(List.of(role.getName()), userDto.getRolesName());
    }

    @Test
    public void givenToListUserDto_whenMapper_ThenCorrect() {
        UserDto userDto = new UserDto(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getDocument(),
                user.getPassword(),
                List.of(role.getName()),
                user.getUserStatus().getName(),
                user.getUserStatus().getId(),
                null
        );

        assertEquals(List.of(userDto), userMapper.toListUserDto(List.of(user)));
    }
}
