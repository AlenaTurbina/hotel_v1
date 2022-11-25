package com.hotel_database.model.repository;

import com.hotel_domain.model.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, UUID> {
    RoomType findRoomTypeByName(String name);

    @Query(value = "SELECT distinct room_type.id as id, room_type.name as name, room_type.quantity_places as quantity_places\n" +
            "FROM\n" +
            "room\n" +
            "JOIN\n" +
            "room_kind ON room.room_kind = room_kind.id\n" +
            "JOIN\n" +
            "room_type ON room_kind.room_type_id = room_type.id", nativeQuery = true)
    List<RoomType> findListUniqueRoomTypeFromRooms();

}
