package com.hotel_server.service.impl;

import com.hotel_database.model.repository.RoomRepository;
import com.hotel_domain.model.entity.ClassApartment;
import com.hotel_domain.model.entity.Room;
import com.hotel_domain.model.entity.RoomKind;
import com.hotel_domain.model.entity.RoomType;
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
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {
    @Mock
    RoomRepository roomRepository;
    @InjectMocks
    RoomServiceImpl roomService;

    private Room room;
    private ClassApartment classApartment1;
    private RoomType roomType1;
    private RoomKind roomKind1;
    private Room room1;

    @BeforeEach
    public void setup() {
        ClassApartment classApartment = ClassApartment.builder()
                .id(UUID.randomUUID())
                .name("CA1")
                .placePrice(10.0)
                .build();
        RoomType roomType = RoomType.builder()
                .id(UUID.randomUUID())
                .name("RT1")
                .quantityPlaces(1)
                .build();
        RoomKind roomKind = RoomKind.builder()
                .id(UUID.randomUUID())
                .roomType(roomType)
                .classApartment(classApartment)
                .roomPrice(10.0)
                .build();
        room = Room.builder()
                .id(UUID.randomUUID())
                .name("Room1")
                .roomKind(roomKind)
                .build();
        classApartment1 = ClassApartment.builder()
                .id(UUID.randomUUID())
                .name("CA2")
                .placePrice(20.0)
                .build();
        roomType1 = RoomType.builder()
                .id(UUID.randomUUID())
                .name("RT2")
                .quantityPlaces(2)
                .build();
        roomKind1 = RoomKind.builder()
                .id(UUID.randomUUID())
                .roomType(roomType1)
                .classApartment(classApartment1)
                .roomPrice(20.0)
                .build();
        room1 = Room.builder()
                .id(UUID.randomUUID())
                .name("Room2")
                .roomKind(roomKind1)
                .build();
    }

    @DisplayName("JUnit test for getAllRooms method")
    @Test
    void test_WhenGetAllRooms_ThenReturnRoomList() {
        given(roomRepository.findAll()).willReturn(List.of(room, room1));
        List<Room> roomList = roomService.getAllRooms();

        assertThat(roomList).isNotEmpty();
        assertThat(roomList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getAllRooms method (empty list)")
    @Test
    void test_WhenGetAllRooms_ThenReturnEmptyRoomList() {
        given(roomRepository.findAll()).willReturn(Collections.emptyList());
        List<Room> roomList = roomService.getAllRooms();

        assertThat(roomList).isEmpty();
        assertThat(roomList.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getRoomById method")
    @Test
    void test_GivenRoomId_WhenGetRoomById_thenReturnRoom() {
        given(roomRepository.findById(room.getId()))
                .willReturn(Optional.of(room));
        Room roomExpected = roomService.getRoomById(room.getId());

        assertThat(roomExpected).isNotNull();
    }

    @DisplayName("JUnit test for getRoomById method (throw exception)")
    @Test
    void test_GivenRoomId_WhenGetRoomById_ThenThrowException() {
        given(roomRepository.findById(room.getId()))
                .willReturn(Optional.empty());

        Assertions.assertThrows(ServerEntityNotFoundException.class,
                () -> roomService.getRoomById(room.getId()));
    }

    @DisplayName("JUnit test for getRoomByName method")
    @Test
    void test_GivenRoomName_WhenFindRoomByName_ThenReturnRoom() {
        given(roomRepository.findRoomByName(room.getName())).willReturn(room);
        Room roomExpected = roomService.getRoomByName(room.getName());

        assertThat(roomExpected).isNotNull();
    }

    @DisplayName("JUnit test for getRoomByName method (return null)")
    @Test
    void test_GivenRoomName_WhenFindRoomByName_ThenReturnNull() {
        given(roomRepository.findRoomByName(room.getName())).willReturn(null);
        Room roomExpected = roomService.getRoomByName(room.getName());

        assertThat(roomExpected).isNull();
    }
}