package org.luismore.hlvsapi.services;

import org.luismore.hlvsapi.domain.dtos.*;
import org.luismore.hlvsapi.domain.entities.Entry;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface EntryService {
    Page<EntryWithHouseNumberDTO> getAllEntries(String filter, Pageable pageable);
    Page<EntryDTO> getEntriesByHouse(UUID houseId, Pageable pageable);
    Page<EntryDTO> getEntriesByUser(UUID userId, Pageable pageable);
    EntryDTO registerAnonymousEntry(EntryAnonymousDTO info, String email);
    EntryTypeCountDTO getVehicleAndPedestrianCounts();
    EntryTypeCountDTO getResidentVisitorAnonymousCounts();
    CombinedEntryTypeCountDTO getCombinedCounts();
    EntryWeekdayCountDTO getWeekdayCounts();
    boolean shouldOpenServoP(String email);
    void sendWebSocketCommand(String url);
    ByteArrayResource exportEntriesToCsv(LocalDate startDate, LocalDate endDate, String entryType);



}
