package com.hotel_server.service.impl;

import com.hotel_domain.model.entity.ClassApartment;
import com.hotel_domain.model.entity.RoomKind;
import com.hotel_domain.model.entity.RoomType;
import com.hotel_database.model.repository.RoomKindRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RoomKindServiceImplTest {
    @Mock
    private RoomKindRepository roomKindRepository;
    @InjectMocks
    private RoomKindServiceImpl roomKindService;

    private RoomKind roomKind;
    private ClassApartment  classApartment1;
    private RoomType roomType1;
    private RoomKind roomKind1;

    @BeforeEach
    public void setup() {
        ClassApartment  classApartment = ClassApartment.builder()
                .id(UUID.randomUUID())
                .name("CA1")
                .placePrice(10.0)
                .build();
        RoomType roomType = RoomType.builder()
                .id(UUID.randomUUID())
                .name("RT1")
                .quantityPlaces(1)
                .build();
        roomKind = RoomKind.builder()
                .id(UUID.randomUUID())
                .roomType(roomType)
                .classApartment(classApartment)
                .roomPrice(10.0)
                .build();
        classApartment1 = ClassApartment.builder()
                .id(UUID.randomUUID())
                .name("CA2")
                .placePrice(10.0)
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
    }

    @DisplayName("JUnit test for getAllRoomKinds method")
    @Test
    void test_WhenGetAllRoomKinds_ThenReturnRoomKindList() {
        given(roomKindRepository.findAll()).willReturn(List.of(roomKind, roomKind1));
        List<RoomKind> roomKindList = roomKindService.getAllRoomKind();

        assertThat(roomKindList).isNotEmpty();
        assertThat(roomKindList.size()).isEqualTo(2);
    }

    @DisplayName("JJUnit test for getAllRoomKinds method (empty list)")
    @Test
    void test_WhenGetAllRoomKinds_ThenReturnEmptyRoomKindsList() {
        given(roomKindRepository.findAll()).willReturn(Collections.emptyList());
        List<RoomKind> roomKindList  = roomKindService.getAllRoomKind();

        assertThat(roomKindList).isEmpty();
        assertThat(roomKindList.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getRoomKindById method")
    @Test
    void test_GivenRoomKindId_WhenGetRoomKindById_thenReturnRoomKind() {
        given(roomKindRepository.findById(roomKind.getId()))
                .willReturn(Optional.of(roomKind));
        RoomKind roomKindExpected = roomKindService.getRoomKindById(roomKind.getId());

        assertThat(roomKindExpected).isNotNull();
    }

    @DisplayName("JUnit test for getRoomKindById method (throw exception)")
    @Test
    void test_GivenRoomKindId_WhenGetRoomKindById_ThenThrowException() {
        given(roomKindRepository.findById(roomKind.getId()))
                .willReturn(Optional.empty());

        Assertions.assertThrows(ServerEntityNotFoundException.class,
                () -> roomKindService.getRoomKindById(roomKind.getId()));
    }

    @DisplayName("JUnit test for getListUniqueRoomKindsFromRooms method")
    @Test
    void test_WhenListUniqueRoomKindsFromRooms_ThenReturnRoomKindList() {
        given(roomKindRepository.findListUniqueRoomKindFromRooms()).willReturn(List.of(roomKind, roomKind1));
        List<RoomKind> roomKindList = roomKindService.getListUniqueRoomKindsFromRooms();

        assertThat(roomKindList).isNotEmpty();
        assertThat(roomKindList.size()).isEqualTo(2);
    }

    @DisplayName("JJUnit test for getListUniqueRoomKindsFromRooms method (empty list)")
    @Test
    void test_WhenListUniqueRoomKindsFromRooms_ThenReturnEmptyRoomKindsList() {
        given(roomKindRepository.findListUniqueRoomKindFromRooms()).willReturn(Collections.emptyList());
        List<RoomKind> roomKindList  = roomKindService.getListUniqueRoomKindsFromRooms();

        assertThat(roomKindList).isEmpty();
        assertThat(roomKindList.size()).isEqualTo(0);
    }

//    @DisplayName("JUnit test for getRoomKindByRoomTypeAndClassApartmentID method")
//    @Test
//    void test_GivenRoomTypeIdAndClassApartmentId_WhenGetRoomKindIdByRoomTypeIdAndClassApartmentID_ThenReturnRoomKind() {
//        given(roomKindRepository.findRoomKindIDByRoomTypeAndClassApartmentID(roomKind.getClassApartment().getId(),
//                roomKind.getRoomType().getId())).willReturn(roomKind.getId());
//       UUID roomKindID = roomKindService.getRoomKindIdByRoomTypeIdAndClassApartmentId(
//               roomKind.getClassApartment().getId(), roomKind.getRoomType().getId());
//
//        assertThat(roomKindID).isNotNull();
//    }

}