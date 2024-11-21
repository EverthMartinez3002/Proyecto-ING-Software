package org.luismore.hlvsapi.repositories;

import org.luismore.hlvsapi.domain.entities.Entry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface EntryRepository extends JpaRepository<Entry, UUID> {
    Page<Entry> findAllByEntryType_Id(String entryTypeId, Pageable pageable);
    Page<Entry> findAllByHouse_Id(UUID houseId, Pageable pageable);
    Page<Entry> findAllByUser_Id(UUID userId, Pageable pageable);
    int countByEntryType_Id(String entryTypeId);
    int countByHouseIsNullAndEntryType_IdNot(String entryTypeId);
    int countByHouseIsNotNull();
    @Query("SELECT e FROM Entry e WHERE e.date BETWEEN :startDate AND :endDate AND (:entryType IS NULL OR e.entryType.type = :entryType)")
    List<Entry> findByDateRangeAndType(@Param("startDate") LocalDate startDate,
                                       @Param("endDate") LocalDate endDate,
                                       @Param("entryType") String entryType);


}
