package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.UserStatus;
import com.hotel_dto.dto.UserStatusDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ComponentScan("com.hotel_domain")
public class UserStatusMapperTest {
    private UserStatusMapper userStatusMapper = Mappers.getMapper(UserStatusMapper.class);
    private UserStatus userStatus;
    public static final String id = "788c07d7-4275-4101-8fc3-1d77986f2129";

    @BeforeEach
    void setUp() {
        userStatus = new UserStatus(UUID.fromString(id), "A");
    }

    @Test
    public void givenToUserStatusDto_whenMapper_ThenCorrect() {
        UserStatusDto userStatusDto = userStatusMapper.toUserStatusDto(userStatus);

        assertEquals(userStatus.getId(), userStatusDto.getId());
        assertEquals(userStatus.getName(), userStatusDto.getName());
    }

    @Test
    public void givenToListUserStatusDto_whenMapper_ThenCorrect() {
        UserStatusDto userStatusDto = new UserStatusDto(
                userStatus.getId(),
                userStatus.getName()
        );

        assertEquals(List.of(userStatusDto), userStatusMapper.toListUserStatusDto(List.of(userStatus)));
    }
}
