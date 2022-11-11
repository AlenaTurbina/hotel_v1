package com.hotel_database.model.repository;

import com.hotel_domain.model.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {
    Room findRoomByName(String name);

    @Query(value = "SELECT room.* from room\n" +
            "WHERE room.id NOT IN (\n" +
            "SELECT room from order_booking \n" +
            "WHERE\n" +
            "order_booking.order_status != ?1 AND\n" +
            "!(date_arrival  >= ?3 OR \n" +
            "date_departure  <= ?2)\n" +
            ")", nativeQuery = true)
    List<Room> findFreeRoomsOnSelectedDates(UUID orderStatusCancelId, LocalDate dateArrival, LocalDate dateDeparture);

    @Query(value = "SELECT room.* from room\n" +
            "JOIN room_kind \n" +
            "ON room.room_kind = room_kind.id\n" +
            "WHERE room.id NOT IN (\n" +
            "SELECT room from order_booking \n" +
            "WHERE\n" +
            "order_booking.order_status != ?1 AND\n" +
            "!(date_arrival >= ?3 OR \n" +
            "date_departure <= ?2)\n" +
            ") \n" +
            "AND\n" +
            "room_kind.room_type_id=?4\n" +
            "AND\n" +
            "room_kind.class_apartment_id=?5", nativeQuery = true)
    List<Room> findListFreeRoomsForBooking(UUID orderStatusCancelId, LocalDate dateArrival, LocalDate dateDeparture,
                                           UUID roomTypeId, UUID classApartmentId);

}
