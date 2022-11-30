package com.hotel_dto.mapper;

import com.hotel_domain.model.entity.OrderStatus;
import com.hotel_dto.dto.OrderStatusDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ComponentScan("com.hotel_domain")
public class OrderStatusMapperTest {
    private OrderStatusMapper orderStatusMapper = Mappers.getMapper(OrderStatusMapper.class);
    private OrderStatus orderStatus;
    public static final String id = "788c07d7-4275-4101-8fc3-1d77986f2129";

    @BeforeEach
    void setUp() {
        orderStatus = new OrderStatus(UUID.fromString(id), "A");
    }

    @Test
    public void givenToOrderStatusDto_whenMapper_ThenCorrect() {
        OrderStatusDto orderStatusDto = orderStatusMapper.toOrderStatusDto(orderStatus);

        assertEquals(orderStatus.getId(), orderStatusDto.getId());
        assertEquals(orderStatus.getName(), orderStatusDto.getName());
    }

    @Test
    public void givenToListOrderStatusDto_whenMapper_ThenCorrect() {
        OrderStatusDto orderStatusDto = new OrderStatusDto(
                orderStatus.getId(),
                orderStatus.getName()
        );

        assertEquals(List.of(orderStatusDto), orderStatusMapper.toListOrderStatusDTO(List.of(orderStatus)));
    }
}
