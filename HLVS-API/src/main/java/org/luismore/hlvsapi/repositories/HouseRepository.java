package org.luismore.hlvsapi.repositories;

import org.luismore.hlvsapi.domain.entities.House;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface HouseRepository extends JpaRepository<House, UUID> {
}