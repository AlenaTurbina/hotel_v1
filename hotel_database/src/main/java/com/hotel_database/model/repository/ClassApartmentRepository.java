package com.hotel_database.model.repository;

import com.hotel_domain.model.entity.ClassApartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassApartmentRepository extends JpaRepository<ClassApartment, Integer> {
    ClassApartment findClassApartmentByName(String name);

    @Query(value = "SELECT distinct class_apartment.id as id, class_apartment.name as name, class_apartment.place_price as place_price\n" +
            "FROM\n" +
            "room\n" +
            "JOIN\n" +
            "room_kind ON room.room_kind = room_kind.id\n" +
            "JOIN\n" +
            "class_apartment ON room_kind.class_apartment_id = class_apartment.id", nativeQuery = true)
    List<ClassApartment> findListUniqueClassApartmentFromRooms();
}
