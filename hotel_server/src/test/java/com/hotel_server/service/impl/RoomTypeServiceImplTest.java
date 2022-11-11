package com.hotel_server.service.impl;

import com.hotel_database.model.repository.RoomTypeRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RoomTypeServiceImplTest {
    @Mock
    private RoomTypeRepository roomTypeRepository;
    @InjectMocks
    private RoomTypeServiceImpl roomTypeService;

    private RoomType roomType;

//    @BeforeEach
//    public void setUp() {
//        roomType = RoomType.builder()
//                .id(1)
//                .name("RT1")
//                .quantityPlaces(1)
//                .build();
//    }
//
//    @DisplayName("JUnit test for getAllRoomTypes method")
//    @Test
//    void test_WhenGetAll_ThenReturnRoomTypeList() {
//        RoomType roomType1 = RoomType.builder()
//                .id(2)
//                .name("RT2")
//                .quantityPlaces(2)
//                .build();
//
//        given(roomTypeRepository.findAll()).willReturn(List.of(roomType, roomType1));
//        List<RoomType> roomTypeList = roomTypeService.getAll();
//
//        assertThat(roomTypeList).isNotEmpty();
//        assertThat(roomTypeList.size()).isEqualTo(2);
//    }
//
//    @DisplayName("JUnit test for getAllRoomTypes method (empty list)")
//    @Test
//    void test_WhenGetAll_ThenReturnEmptyRoomTypeList() {
//        RoomType roomType1 = RoomType.builder()
//                .id(2)
//                .name("RT2")
//                .quantityPlaces(2)
//                .build();
//
//        given(roomTypeRepository.findAll()).willReturn(Collections.emptyList());
//        List<RoomType> roomTypeList = roomTypeService.getAll();
//
//        assertThat(roomTypeList).isEmpty();
//        assertThat(roomTypeList.size()).isEqualTo(0);
//    }
//
//    @DisplayName("JUnit test for getRoomTypeById method")
//    @Test
//    void test_GivenRoomTypeId_WhenGetRoomTypeById_thenReturnRoomType() {
//        given(roomTypeRepository.findById(roomType.getId()))
//                .willReturn(Optional.of(roomType));
//        RoomType roomTypeExpected = roomTypeService.getRoomTypeById(roomType.getId());
//
//        assertThat(roomTypeExpected).isNotNull();
//    }
//
//    @DisplayName("JUnit test for getRoomTypeById method (throw exception)")
//    @Test
//    void test_GivenRoomTypeId_WhenGetRoomTypeById_ThenThrowException() {
//        given(roomTypeRepository.findById(roomType.getId()))
//                .willReturn(Optional.empty());
//
//        Assertions.assertThrows(ServerEntityNotFoundException.class,
//                () -> roomTypeService.getRoomTypeById(roomType.getId()));
//    }
//
//    @DisplayName("JUnit test for getRoomTypeByName method")
//    @Test
//    void test_GivenRoomTypeName_WhenFindRoomTypeByName_ThenReturnRoomType() {
//        given(roomTypeRepository.findRoomTypeByName(roomType.getName())).willReturn(roomType);
//        RoomType roomTypeExpected = roomTypeService.getRoomTypeByName(roomType.getName());
//
//        assertThat(roomTypeExpected).isNotNull();
//    }
//
//    @DisplayName("JUnit test for getRoomTypeByName method (return null)")
//    @Test
//    void test_GivenRoomTypeName_WhenFindRoomTypeByName_ThenReturnNull() {
//        given(roomTypeRepository.findRoomTypeByName(roomType.getName())).willReturn(null);
//        RoomType roomTypeExpected = roomTypeService.getRoomTypeByName(roomType.getName());
//
//        assertThat(roomTypeExpected).isNull();
//    }
//
//    @DisplayName("JUnit test for getListUniqueRoomTypesFromRooms method")
//    @Test
//    void test_WhenListUniqueRoomTypesFromRooms_thenReturnRoomTypeList() {
//        RoomType roomType1 = RoomType.builder()
//                .id(2)
//                .name("RT2")
//                .quantityPlaces(2)
//                .build();
//        given(roomTypeRepository.findListUniqueRoomTypeFromRooms()).willReturn(List.of(roomType, roomType1));
//        List<RoomType> roomTypeList = roomTypeService.getListUniqueRoomTypesFromRooms();
//
//        assertThat(roomTypeList).isNotEmpty();
//        assertThat(roomTypeList.size()).isEqualTo(2);
//    }
//
//    @DisplayName("JUnit test for getListUniqueRoomTypesFromRooms method (empty list)")
//    @Test
//    void test_WhenListUniqueRoomTypesFromRooms_ThenReturnEmptyRoomTypeList() {
//        RoomType roomType1 = RoomType.builder()
//                .id(2)
//                .name("RT2")
//                .quantityPlaces(2)
//                .build();
//        given(roomTypeRepository.findListUniqueRoomTypeFromRooms()).willReturn(Collections.emptyList());
//        List<RoomType> roomTypeList = roomTypeService.getListUniqueRoomTypesFromRooms();
//
//        assertThat(roomTypeList).isEmpty();
//        assertThat(roomTypeList.size()).isEqualTo(0);
//    }
}