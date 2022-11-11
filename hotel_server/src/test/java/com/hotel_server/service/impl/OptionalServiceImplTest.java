package com.hotel_server.service.impl;

import com.hotel_domain.model.entity.Optional;
import com.hotel_database.model.repository.OptionalRepository;
import com.hotel_domain.model.entity.RoomType;
import com.hotel_server.exceptionHandler.exception.ServerEntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OptionalServiceImplTest {
    @Mock
    private OptionalRepository optionalRepository;
    @InjectMocks
    private OptionalServiceImpl optionalService;

    private Optional optional;

//    @BeforeEach
//    public void setup() {
//        optional = Optional.builder()
//                .id(1)
//                .name("O1")
//                .optionalPrice(10.0)
//                .build();
//    }
//
//    @DisplayName("JUnit test for getAllOptional method")
//    @Test
//    void test_WhenGetAllOptional_ThenReturnOptionalList() {
//        Optional optional1 = Optional.builder()
//                .id(1)
//                .name("O1")
//                .optionalPrice(10.0)
//                .build();
//        given(optionalRepository.findAll()).willReturn(List.of(optional, optional1));
//        List<Optional> optionalList = optionalService.getAllOptionals();
//
//        assertThat(optionalList).isNotEmpty();
//        assertThat(optionalList.size()).isEqualTo(2);
//    }
//
//    @DisplayName("JUnit test for getAllOptional method (empty list)")
//    @Test
//    void test_WhenGetAllOptional_ThenReturnEmptyOptionalList() {
//        Optional optional1 = Optional.builder()
//                .id(1)
//                .name("O1")
//                .optionalPrice(10.0)
//                .build();
//        given(optionalRepository.findAll()).willReturn(Collections.emptyList());
//        List<Optional> optionalList = optionalService.getAllOptionals();
//
//        assertThat(optionalList).isEmpty();
//        assertThat(optionalList.size()).isEqualTo(0);
//    }
//
//    @DisplayName("JUnit test for getOptionalById method")
//    @Test
//    void test_GivenOptionalId_WhenGetOptionalById_thenReturnOptional() {
//        given(optionalRepository.findById(optional.getId()))
//                .willReturn(java.util.Optional.of(optional));
//        Optional optionalExpected = optionalService.getOptionalById(optional.getId());
//
//        assertThat(optionalExpected).isNotNull();
//    }
//
//    @DisplayName("JUnit test for getOptionalById method (throw exception)")
//    @Test
//    void test_GivenOptionalId_WhenGetOptionalById_ThenThrowException() {
//        given(optionalRepository.findById(optional.getId()))
//                .willReturn(java.util.Optional.empty());
//
//        Assertions.assertThrows(ServerEntityNotFoundException.class,
//                () -> optionalService.getOptionalById(optional.getId()));
//    }
//
//    @DisplayName("JUnit test for getOptionalByName method")
//    @Test
//    void test_GivenOptionalName_WhenFindOptionalByName_ThenReturnOptional() {
//        given(optionalRepository.findOptionalByName(optional.getName())).willReturn(optional);
//        Optional optionalExpected = optionalService.getOptionalByName(optional.getName());
//
//        assertThat(optionalExpected).isNotNull();
//    }
//
//    @DisplayName("JUnit test for getOptionalByName method (return null)")
//    @Test
//    void test_GivenOptionalName_WhenFindOptionalByName_ThenReturnNull() {
//        given(optionalRepository.findOptionalByName(optional.getName())).willReturn(null);
//        Optional optionalExpected = optionalService.getOptionalByName(optional.getName());
//
//        assertThat(optionalExpected).isNull();
//    }
//
//    @DisplayName("JUnit test for getListOptionalById method")
//    @Test
//    void test_GivenListOptionalId_WhenGetListOptionalById_thenReturnListOptional() {
//        Optional optional1 = Optional.builder()
//                .id(1)
//                .name("O1")
//                .optionalPrice(10.0)
//                .build();
//        List<Integer> listOptionalId = new ArrayList<>(List.of(optional.getId(), optional1.getId()));
//        given(optionalRepository.findById(optional.getId()))
//                .willReturn(java.util.Optional.of(optional));
//        given(optionalRepository.findById(optional1.getId()))
//                .willReturn(java.util.Optional.of(optional1));
//        List <Optional> optionalListExpected = optionalService.getListOptionalById(listOptionalId);
//
//        assertThat(optionalListExpected).isNotNull();
//        assertThat(optionalListExpected.size()).isEqualTo(2);
//    }
//
//    @DisplayName("JUnit test for getListOptionalById method (throw exception)")
//    @Test
//    void test_GivenListOptionalId_WhenGetListOptionalById_thenThrowException() {
//        Optional optional1 = Optional.builder()
//                .id(2)
//                .name("O1")
//                .optionalPrice(10.0)
//                .build();
//        List<Integer> listOptionalId = new ArrayList<>(List.of(optional.getId(), optional1.getId()));
//        given(optionalRepository.findById(optional.getId()))
//                .willReturn(java.util.Optional.of(optional));
//        given(optionalRepository.findById(optional1.getId()))
//                .willReturn(java.util.Optional.empty());
//
//        Assertions.assertThrows(ServerEntityNotFoundException.class,
//                () -> optionalService.getListOptionalById(listOptionalId));
//    }
}