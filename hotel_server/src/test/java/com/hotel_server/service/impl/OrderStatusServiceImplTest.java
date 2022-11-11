package com.hotel_server.service.impl;

import com.hotel_database.model.repository.OrderStatusRepository;
import com.hotel_domain.model.entity.OrderStatus;
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
class OrderStatusServiceImplTest {
    @Mock
    private OrderStatusRepository orderStatusRepository;
    @InjectMocks
    private OrderStatusServiceImpl orderStatusService;

    private OrderStatus orderStatus;

    @BeforeEach
    public void setUp() {
        orderStatus = OrderStatus.builder()
                .id(UUID.randomUUID())
                .name("OS")
                .build();
    }

    @DisplayName("JUnit test for getAllOrderStatuses method")
    @Test
    void test_WhenGetAll_ThenReturnOrderStatusList() {
        OrderStatus orderStatus1 = OrderStatus.builder()
                .id(UUID.randomUUID())
                .name("OS1")
                .build();
        given(orderStatusRepository.findAll()).willReturn(List.of(orderStatus, orderStatus1));
        List<OrderStatus> orderStatusList = orderStatusService.getAllOrderStatuses();

        assertThat(orderStatusList).isNotEmpty();
        assertThat((orderStatusList).size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getAllOrderStatuses method (empty list)")
    @Test
    void test_WhenGetAll_ThenReturnEmptyOrderStatusList() {
        given(orderStatusRepository.findAll()).willReturn(Collections.emptyList());
        List<OrderStatus> orderStatusList = orderStatusService.getAllOrderStatuses();

        assertThat(orderStatusList).isEmpty();
        assertThat((orderStatusList).size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getOrderStatusById method")
    @Test
    void test_GivenOrderStatusId_WhenGetOrderStatusById_thenReturnOrderStatus() {
        given(orderStatusRepository.findById(orderStatus.getId())).willReturn(Optional.of(orderStatus));
        OrderStatus orderStatusExpected = orderStatusService.getOrderStatusById(orderStatus.getId());

        assertThat(orderStatusExpected).isNotNull();
    }

    @DisplayName("JUnit test for getOrderStatusById method (throw exception")
    @Test
    void test_GivenOrderStatusId_WhenGetOrderStatusById_thenThrowException() {
        given(orderStatusRepository.findById(orderStatus.getId())).willReturn(Optional.empty());

        Assertions.assertThrows(ServerEntityNotFoundException.class, () ->
                orderStatusService.getOrderStatusById(orderStatus.getId()));
    }

    @DisplayName("JUnit test for getOrderStatusByName method")
    @Test
    void test_GivenOrderStatusName_WhenFindOrderStatusByName_ThenReturnOrderStatus() {
        given(orderStatusRepository.findOrderStatusByName(orderStatus.getName())).willReturn(orderStatus);
        OrderStatus orderStatusExpected = orderStatusService.getOrderStatusByName(orderStatus.getName());

        assertThat(orderStatusExpected).isNotNull();
    }

    @DisplayName("JUnit test for getOrderStatusByName method (return null)")
    @Test
    void test_GivenOrderStatusName_WhenFindOrderStatusByName_ThenReturnNull() {
        given(orderStatusRepository.findOrderStatusByName(orderStatus.getName())).willReturn(null);
        OrderStatus orderStatusExpected = orderStatusService.getOrderStatusByName(orderStatus.getName());

        assertThat(orderStatusExpected).isNull();
    }
}