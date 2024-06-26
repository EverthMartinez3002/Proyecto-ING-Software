package org.luismore.hlvsapi.repositories;

import org.luismore.hlvsapi.domain.entities.QR;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface QrRepository extends JpaRepository<QR, UUID> {
    Optional<QR> findByToken(String token);
}
