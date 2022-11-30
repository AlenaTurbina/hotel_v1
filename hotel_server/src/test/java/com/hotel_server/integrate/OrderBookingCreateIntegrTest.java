package com.hotel_server.integrate;

import com.hotel_server.message.Messages;
import com.hotel_database.model.repository.*;
import com.hotel_domain.model.entity.*;
import com.hotel_dto.dto.OrderBookingDto;
import com.hotel_dto.mapper.OrderBookingMapper;
import com.hotel_server.service.impl.*;
import com.hotel_server.validator.OrderBookingValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static com.hotel_server.util.Utils.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan("com.hotel_dto")
class OrderBookingCreateIntegrTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    OrderBookingMapper orderBookingMapper;

    @MockBean
    private OrderBookingValidator orderBookingValidator;
    @MockBean
    private OrderBookingRepository orderBookingRepository;
    @MockBean
    private RoomRepository roomRepository;
    @MockBean
    private RoomServiceImpl roomService;
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private OptionalServiceImpl optionalService;
    @MockBean
    private OrderStatusServiceImpl orderStatusService;
    @MockBean
    private  EmailSenderServiceImpl emailSenderService;

    private static final UUID ID_DEFAULT_ORDER_STATUS_PAID = Messages.getUUIDMessage("server.booking.idDefaultOrderStatusPaid");

    private User user;
    private Room room1;
    private Room room2;
    private OrderStatus orderStatusPaid;
    private Optional optional1;

    private UUID uuidRoomType = UUID.randomUUID();
    private UUID uuidClassApartment = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        orderStatusPaid = OrderStatus.builder()
                .id(ID_DEFAULT_ORDER_STATUS_PAID)
                .name("Paid")
                .build();
        ClassApartment classApartment1 = ClassApartment.builder()
                .id(uuidRoomType)
                .name("CA1")
                .placePrice(20.0)
                .build();
        RoomType roomType1 = RoomType.builder()
                .id(uuidRoomType)
                .name("RT1")
                .quantityPlaces(2)
                .build();
        RoomKind roomKind1 = RoomKind.builder()
                .id(UUID.randomUUID())
                .classApartment(classApartment1)
                .roomType(roomType1)
                .roomPrice(100.0)
                .build();
        room1 = Room.builder()
                .id(UUID.randomUUID())
                .name("R1")
                .roomKind(roomKind1)
                .build();
        room2 = Room.builder()
                .id(UUID.randomUUID())
                .name("R2")
                .roomKind(roomKind1)
                .build();
        optional1 = Optional.builder()
                .id(UUID.randomUUID())
                .name("O1")
                .optionalPrice(1.0)
                .build();
        UserStatus userStatus1 = UserStatus.builder()
                .id(UUID.randomUUID())
                .name("US1")
                .build();
        Role role1 = Role.builder()
                .id(UUID.randomUUID())
                .name("R1")
                .build();
        List<Role> roles = new ArrayList<>(List.of(role1));
        user = User.builder()
                .id(UUID.randomUUID())
                .email("user@test.com")
                .userStatus(userStatus1)
                .roles(roles)
                .build();
    }

    @Test
    void testRegisterOrderBookingSuccessfulAndExpectStatusCreated() throws Exception {
        //OrderBookingDTO from BookingForm
        OrderBookingDto orderBookingDto1 = new OrderBookingDto();
        orderBookingDto1.setDateArrival(LocalDate.of(2022, 05, 05));
        orderBookingDto1.setDateDeparture(LocalDate.of(2022, 05, 10));
        orderBookingDto1.setQuantityPersons(1);
        orderBookingDto1.setUserId(user.getId());
        orderBookingDto1.setRoomTypeId(uuidRoomType);
        orderBookingDto1.setClassApartmentId(uuidClassApartment);

        List<Room> rooms = new ArrayList<>(List.of(room1, room2));
        Mockito.when(roomRepository.findListFreeRoomsForBooking(any(), any(), any(), any(), any())).thenReturn(rooms);

        Mockito.when(orderBookingValidator.supports(any())).thenReturn(true);

        Mockito.when(userService.getUserByEmail(any())).thenReturn(user);
        List<Optional> optionalList = new ArrayList<>(List.of(optional1));
        Mockito.when(optionalService.getListOptionalById(any())).thenReturn(optionalList);
        Mockito.when(orderStatusService.getOrderStatusById(any())).thenReturn(orderStatusPaid);

        int daysRent = 10 - 5; //Booking period: 2022-05-05 - 2022-05-10
        double sumTotal = daysRent * room1.getRoomKind().getRoomPrice()
                + daysRent * room1.getRoomKind().getClassApartment().getPlacePrice() * orderBookingDto1.getQuantityPersons()
                + daysRent * optional1.getOptionalPrice();

        OrderBooking orderBookingBuild = OrderBooking.builder()
                .date(LocalDate.now())
                .dateArrival(LocalDate.of(2022, 05, 05))
                .dateDeparture(LocalDate.of(2022, 05, 10))
                .quantityPersons(1)
                .client(user)
                .room(room1)
                .sumTotal(sumTotal)
                .orderStatus(orderStatusPaid)
                .optionals(optionalList)
                .build();

        mockMvc.perform(post("/api/orderBooking/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(orderBookingDto1)))
                .andExpect(status().isCreated())
                .andExpect(content().json(asJsonString(orderBookingMapper.toOrderBookingDto(orderBookingBuild))))
                .andDo(print());
    }

    @Test
    void testRegisterOrderBookingSuccessfulAndExpectStatusRefusal() throws Exception {
        //OrderBookingDTO from BookingForm
        OrderBookingDto orderBookingDto1 = new OrderBookingDto();
        orderBookingDto1.setDateArrival(LocalDate.of(2022, 05, 05));
        orderBookingDto1.setDateDeparture(LocalDate.of(2022, 05, 10));
        orderBookingDto1.setQuantityPersons(1);
        orderBookingDto1.setUserId(user.getId());
        orderBookingDto1.setRoomTypeId(uuidRoomType);
        orderBookingDto1.setClassApartmentId(uuidClassApartment);

        List<Room> rooms = new ArrayList<>(List.of(room1, room2));
        Mockito.when(roomRepository.findListFreeRoomsForBooking(any(), any(), any(), any(), any()))
                .thenReturn(Collections.emptyList());

        Mockito.when(orderBookingValidator.supports(any())).thenReturn(true);

        Mockito.when(userService.getUserByEmail(any())).thenReturn(user);
        List<Optional> optionalList = new ArrayList<>(List.of(optional1));
        Mockito.when(optionalService.getListOptionalById(any())).thenReturn(optionalList);
        Mockito.when(orderStatusService.getOrderStatusById(any())).thenReturn(orderStatusPaid);

        mockMvc.perform(post("/api/orderBooking/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(asJsonString(orderBookingDto1)))
                .andExpect(status().isResetContent())
                .andDo(print());
    }

}