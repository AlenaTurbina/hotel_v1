package com.hotel_database.model.repository;

import com.hotel_domain.model.entity.RoomKind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public interface RoomKindRepository extends JpaRepository<RoomKind, UUID> {

    @Query(value = "SELECT * FROM room_kind WHERE id IN \n" +
            "(SELECT DISTINCT room_kind FROM room)", nativeQuery = true)
    List<RoomKind> findListUniqueRoomKindFromRooms();

    @Query(value = "SELECT room_kind.id, room_kind.room_type_id, room_kind.class_apartment_id, room_kind.room_price, \n" +
            "CAST(SUM(CASE WHEN room.id NOT IN \n" +
            "(SELECT room from order_booking \n" +
            "WHERE\n" +
            "order_booking.order_status != ?1 AND\n" +
            "!(date_arrival  >= ?3 OR \n" +
            "date_departure  <= ?2)\n" +
            ") \n" +
            "THEN '1' ELSE '0' END) AS UNSIGNED) AS SUMMA\n" +
            "from room_kind\n" +
            "JOIN room\n" +
            "ON room_kind.id = room.room_kind\n" +
            "GROUP BY room_kind.id", nativeQuery = true)
    ArrayList<Object[]> findQuantityFreeRoomsWithRoomKinds(Integer orderStatusCancelId,
                                                           LocalDate dateArrival, LocalDate dateDeparture);

    @Query(value = "SELECT room_kind.id\n" +
            "FROM room_kind\n" +
            "WHERE\n" +
            "room_kind.class_apartment_id = ?1 AND room_kind.room_type_id = ?2", nativeQuery = true)
    UUID findRoomKindIDByRoomTypeAndClassApartmentID(UUID classApartmentId, UUID roomTypeId);

}
