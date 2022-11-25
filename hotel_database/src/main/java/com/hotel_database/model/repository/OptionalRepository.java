package com.hotel_database.model.repository;

import com.hotel_domain.model.entity.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OptionalRepository extends JpaRepository<Optional, UUID> {
    Optional findOptionalByName(String name);
}
