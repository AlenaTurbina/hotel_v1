package com.hotel_server.integrate;

import com.hotel_server.message.Messages;
import com.hotel_database.model.repository.*;
import com.hotel_domain.model.entity.*;
import com.hotel_dto.dto.OrderBookingDTO;
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

    private static final Integer ID_DEFAULT_ORDER_STATUS_PAID = Messages.getIntegerMessage("server.booking.idDefaultOrderStatusPaid");

    private User user;
    private Room room1;
    private Room room2;
    private OrderStatus orderStatusPaid;
    private Optional optional1;

//    @BeforeEach
//    public void setUp() {
//        orderStatusPaid = OrderStatus.builder()
//                .id(ID_DEFAULT_ORDER_STATUS_PAID)
//                .name("Paid")
//                .build();
//        ClassApartment classApartment1 = ClassApartment.builder()
//                .id(1)
//                .name("CA1")
//                .placePrice(20.0)
//                .build();
//        RoomType roomType1 = RoomType.builder()
//                .id(1)
//                .name("RT1")
//                .quantityPlaces(2)
//                .build();
//        RoomKind roomKind1 = RoomKind.builder()
//                .id(1)
//                .classApartment(classApartment1)
//                .roomType(roomType1)
//                .roomPrice(100.0)
//                .build();
//        room1 = Room.builder()
//                .id(1)
//                .name("R1")
//                .roomKind(roomKind1)
//                .build();
//        room2 = Room.builder()
//                .id(2)
//                .name("R2")
//                .roomKind(roomKind1)
//                .build();
//        optional1 = Optional.builder()
//                .id(1)
//                .name("O1")
//                .optionalPrice(1.0)
//                .build();
//        UserStatus userStatus1 = UserStatus.builder()
//                .id(1)
//                .name("US1")
//                .build();
//        Role role1 = Role.builder()
//                .id(1)
//                .name("R1")
//                .build();
//        List<Role> roles = new ArrayList<>(List.of(role1));
//        user = User.builder()
//                .id(1)
//                .email("user@test.com")
//                .userStatus(userStatus1)
//                .roles(roles)
//                .build();
//    }
//
//    @Test
//    void testRegisterOrderBookingSuccessfulAndExpectStatusCreated() throws Exception {
//        //OrderBookingDTO from BookingForm
//        OrderBookingDTO orderBookingDTO1 = new OrderBookingDTO();
//        orderBookingDTO1.setDateArrival(LocalDate.of(2022, 05, 05));
//        orderBookingDTO1.setDateDeparture(LocalDate.of(2022, 05, 10));
//        orderBookingDTO1.setQuantityPersons(1);
//        orderBookingDTO1.setUser(user.getId());
//        orderBookingDTO1.setRoomType(1);
//        orderBookingDTO1.setClassApartment(1);
//
//        List<Room> rooms = new ArrayList<>(List.of(room1, room2));
//        Mockito.when(roomRepository.findListFreeRoomsForBooking(any(), any(), any(), any(), any())).thenReturn(rooms);
//
//        Mockito.when(orderBookingValidator.supports(any())).thenReturn(true);
//
//        Mockito.when(userService.getUserByEmail(any())).thenReturn(user);
//        List<Optional> optionalList = new ArrayList<>(List.of(optional1));
//        Mockito.when(optionalService.getListOptionalById(any())).thenReturn(optionalList);
//        Mockito.when(orderStatusService.getOrderStatusById(any())).thenReturn(orderStatusPaid);
//
//        int daysRent = 10 - 5; //Booking period: 2022-05-05 - 2022-05-10
//        double sumTotal = daysRent * room1.getRoomKind().getRoomPrice()
//                + daysRent * room1.getRoomKind().getClassApartment().getPlacePrice() * orderBookingDTO1.getQuantityPersons()
//                + daysRent * optional1.getOptionalPrice();
//
//        OrderBooking orderBookingBuild = OrderBooking.builder()
//                .date(LocalDate.now())
//                .dateArrival(LocalDate.of(2022, 05, 05))
//                .dateDeparture(LocalDate.of(2022, 05, 10))
//                .quantityPersons(1)
//                .client(user)
//                .room(room1)
//                .sumTotal(sumTotal)
//                .orderStatus(orderStatusPaid)
//                .optionals(optionalList)
//                .build();
//
//        mockMvc.perform(post("/api/orderBooking/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(orderBookingDTO1)))
//                .andExpect(status().isCreated())
//                .andExpect(content().json(asJsonString(orderBookingMapper.toOrderBookingDTO(orderBookingBuild))))
//                .andDo(print());
//    }
//
//    @Test
//    void testRegisterOrderBookingSuccessfulAndExpectStatusRefusal() throws Exception {
//        //OrderBookingDTO from BookingForm
//        OrderBookingDTO orderBookingDTO1 = new OrderBookingDTO();
//        orderBookingDTO1.setDateArrival(LocalDate.of(2022, 05, 05));
//        orderBookingDTO1.setDateDeparture(LocalDate.of(2022, 05, 10));
//        orderBookingDTO1.setQuantityPersons(1);
//        orderBookingDTO1.setUser(user.getId());
//        orderBookingDTO1.setRoomType(1);
//        orderBookingDTO1.setClassApartment(1);
//
//        List<Room> rooms = new ArrayList<>(List.of(room1, room2));
//        Mockito.when(roomRepository.findListFreeRoomsForBooking(any(), any(), any(), any(), any()))
//                .thenReturn(Collections.emptyList());
//
//        Mockito.when(orderBookingValidator.supports(any())).thenReturn(true);
//
//        Mockito.when(userService.getUserByEmail(any())).thenReturn(user);
//        List<Optional> optionalList = new ArrayList<>(List.of(optional1));
//        Mockito.when(optionalService.getListOptionalById(any())).thenReturn(optionalList);
//        Mockito.when(orderStatusService.getOrderStatusById(any())).thenReturn(orderStatusPaid);
//
//        mockMvc.perform(post("/api/orderBooking/create")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(asJsonString(orderBookingDTO1)))
//                .andExpect(status().isResetContent())
//                .andDo(print());
//    }

}