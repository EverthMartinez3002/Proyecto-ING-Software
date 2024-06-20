package org.luismore.hlvsapi.repositories;

import org.luismore.hlvsapi.domain.entities.LimitTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LimitTimeRepository extends JpaRepository<LimitTime, Integer> {
}