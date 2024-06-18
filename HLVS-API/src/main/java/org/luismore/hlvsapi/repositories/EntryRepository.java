package org.luismore.hlvsapi.repositories;

import org.luismore.hlvsapi.domain.entities.Entry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EntryRepository extends JpaRepository<Entry, UUID> {
}
