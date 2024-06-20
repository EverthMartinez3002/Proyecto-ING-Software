package org.luismore.hlvsapi.repositories;

import org.luismore.hlvsapi.domain.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RequestRepository extends JpaRepository<Request, UUID> {

    @Query("SELECT r FROM Request r WHERE r.house.id = :houseId AND r.state.id = :stateId")
    List<Request> findByHouseIdAndState(@Param("houseId") UUID houseId, @Param("stateId") String stateId);

    List<Request> findByHouseId(UUID houseId);

    Optional<Request> findById(UUID requestId);

    @Query("SELECT r FROM Request r WHERE r.house.id = :houseId AND r.state.id = :stateId AND r.hour1 IS NULL AND r.hour2 IS NULL")
    List<Request> findUniquePendingRequestsByHouseId(@Param("houseId") UUID houseId, @Param("stateId") String stateId);

    @Query("SELECT r FROM Request r WHERE r.state.id = :stateId AND r.entryTime IS NULL AND r.house.id = :houseId")
    List<Request> findMultiplePendingRequestsByHouseId(@Param("houseId") UUID houseId, @Param("stateId") String stateId);
}