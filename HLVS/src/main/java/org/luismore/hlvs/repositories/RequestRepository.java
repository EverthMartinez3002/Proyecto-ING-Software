package org.luismore.hlvs.repositories;

import org.luismore.hlvs.domain.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    List<Request> findByHomeIdAndState(Long homeId, String state);

    @Modifying
    @Query("UPDATE Request r SET r.qrDuration = :newDuration")
    void updateQrDuration(@Param("newDuration") Long newDuration);
}
