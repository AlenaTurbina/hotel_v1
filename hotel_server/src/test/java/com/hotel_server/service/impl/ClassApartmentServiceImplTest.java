package com.hotel_server.service.impl;

import com.hotel_domain.model.entity.ClassApartment;
import com.hotel_database.model.repository.ClassApartmentRepository;
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
class ClassApartmentServiceImplTest {
    @Mock
    private ClassApartmentRepository classApartmentRepository;
    @InjectMocks
    private ClassApartmentServiceImpl classApartmentService;

    private ClassApartment classApartment;
    private ClassApartment classApartment1;

    @BeforeEach
    public void setUp() {
        classApartment = ClassApartment.builder()
                .id(UUID.randomUUID())
                .name("CA1")
                .placePrice(10.0)
                .build();
       classApartment1 = ClassApartment.builder()
                .id(UUID.randomUUID())
                .name("CA2")
                .placePrice(20.0)
                .build();
    }

    @DisplayName("JUnit test for getAllClassApartment method")
    @Test
    void test_WhenGetAll_ThenReturnClassApartmentList(){
        given(classApartmentRepository.findAll()).willReturn(List.of(classApartment, classApartment1));
        List<ClassApartment> classApartmentList = classApartmentService.getAllClassApartments();

        assertThat(classApartmentList).isNotEmpty();
        assertThat(classApartmentList.size()).isEqualTo(2);
    }
//
    @DisplayName("JUnit test for getAllClassApartment method (empty list)")
    @Test
    void test_WhenGetAll_ThenReturnEmptyClassApartmentList(){
        given(classApartmentRepository.findAll()).willReturn(Collections.emptyList());
        List<ClassApartment> classApartmentList = classApartmentService.getAllClassApartments();

        assertThat(classApartmentList).isEmpty();
        assertThat(classApartmentList.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getById ClassApartment method")
    @Test
    void test_GivenClassApartmentId_WhenGetById_thenReturnClassApartment() {
        given(classApartmentRepository.findById(classApartment.getId()))
                .willReturn(Optional.of(classApartment));
        ClassApartment classApartmentExpected = classApartmentService.getClassApartmentById(classApartment.getId());

        assertThat(classApartmentExpected).isNotNull();
    }

    @DisplayName("JUnit test for getById ClassApartment method (throw exception)")
    @Test
    void test_GivenClassApartmentId_WhenGetById_ThenThrowException() {
        given(classApartmentRepository.findById(classApartment.getId()))
                .willReturn(Optional.empty());

        Assertions.assertThrows(ServerEntityNotFoundException.class,
                () -> classApartmentService.getClassApartmentById(classApartment.getId()));
    }

    @DisplayName("JUnit test for getClassApartmentByName method")
    @Test
    void test_GivenClassApartmentName_WhenFindClassApartmentByName_ThenReturnClassApartment() {
        given(classApartmentRepository.findClassApartmentByName(classApartment.getName())).willReturn(classApartment);
        ClassApartment classApartmentExpected = classApartmentService.getClassApartmentByName(classApartment.getName());

        assertThat(classApartmentExpected).isNotNull();
    }

    @DisplayName("JUnit test for getClassApartmentByName method (return null)")
    @Test
    void test_GivenClassApartmentName_WhenFindClassApartmentByName_ThenReturnNull() {
        given(classApartmentRepository.findClassApartmentByName(classApartment.getName())).willReturn(null);
        ClassApartment classApartmentExpected = classApartmentService.getClassApartmentByName(classApartment.getName());

        assertThat(classApartmentExpected).isNull();
    }

    @DisplayName("JUnit test for getListUniqueClassApartmentsFromRooms method")
    @Test
    void test_WhenListUniqueClassApartmentsFromRooms_thenReturnClassApartmentList(){
        given(classApartmentRepository.findListUniqueClassApartmentFromRooms()).willReturn(List.of(classApartment, classApartment1));
        List<ClassApartment> classApartmentList = classApartmentService.getListUniqueClassApartmentsFromRooms();

        assertThat(classApartmentList).isNotEmpty();
        assertThat(classApartmentList.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getListUniqueClassApartmentsFromRooms method (empty list)")
    @Test
    void test_WhenListUniqueClassApartmentsFromRooms_ThenReturnEmptyClassApartmentList(){
        given(classApartmentRepository.findListUniqueClassApartmentFromRooms()).willReturn(Collections.emptyList());
        List<ClassApartment> classApartmentList = classApartmentService.getListUniqueClassApartmentsFromRooms();

        assertThat(classApartmentList).isEmpty();
        assertThat(classApartmentList.size()).isEqualTo(0);
    }

}