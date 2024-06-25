package org.luismore.hlvsapi.repositories;

import org.luismore.hlvsapi.domain.entities.QR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QrRepository extends JpaRepository<QR, UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE QR q SET q.duration = :duration")
    void updateQrExpiration(@Param("duration") int duration);

    Optional<QR> findByUserId(UUID userId);
}
