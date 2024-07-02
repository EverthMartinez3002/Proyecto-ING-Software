package org.luismore.hlvsapi.services.impls;

import org.luismore.hlvsapi.domain.dtos.*;
import org.luismore.hlvsapi.domain.entities.Entry;
import org.luismore.hlvsapi.domain.entities.EntryType;
import org.luismore.hlvsapi.domain.entities.User;
import org.luismore.hlvsapi.repositories.EntryRepository;
import org.luismore.hlvsapi.repositories.EntryTypeRepository;
import org.luismore.hlvsapi.repositories.UserRepository;
import org.luismore.hlvsapi.services.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EntryServiceImpl implements EntryService {

    private final EntryRepository entryRepository;
    private final EntryTypeRepository entryTypeRepository;
    private final UserRepository userRepository;

    @Autowired
    public EntryServiceImpl(EntryRepository entryRepository, EntryTypeRepository entryTypeRepository, UserRepository userRepository) {
        this.entryRepository = entryRepository;
        this.entryTypeRepository = entryTypeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Page<EntryWithHouseNumberDTO> getAllEntries(String filter, Pageable pageable) {
        if (filter != null && !filter.isEmpty()) {
            return entryRepository.findAllByEntryType_Id(filter, pageable).map(this::toEntryWithHouseNumberDTO);
        } else {
            return entryRepository.findAll(pageable).map(this::toEntryWithHouseNumberDTO);
        }
    }

    @Override
    public Page<EntryDTO> getEntriesByHouse(UUID houseId, Pageable pageable) {
        return entryRepository.findAllByHouse_Id(houseId, pageable).map(this::toDTO);
    }

    @Override
    public Page<EntryDTO> getEntriesByUser(UUID userId, Pageable pageable) {
        return entryRepository.findAllByUser_Id(userId, pageable).map(this::toDTO);
    }

    @Override
    public EntryDTO registerAnonymousEntry(EntryAnonymousDTO info) {
        Entry entry = new Entry();
        entry.setEntryTime(LocalTime.now());
        entry.setDate(LocalDate.now());

        EntryType entryType = entryTypeRepository.findTypeByType("anonymous");
        entry.setEntryType(entryType);

        entry.setComment(info.getComment());
        entry.setHeadline(info.getHeadline());

        entryRepository.save(entry);
        return null;
    }

    @Override
    public EntryTypeCountDTO getVehicleAndPedestrianCounts() {
        int vehicleCount = entryRepository.countByEntryType_Id("VEHI");
        int pedestrianCount = entryRepository.countByEntryType_Id("PEDE");

        EntryTypeCountDTO dto = new EntryTypeCountDTO();
        dto.setData(Arrays.asList(vehicleCount, pedestrianCount));
        return dto;
    }

    @Override
    public EntryTypeCountDTO getResidentVisitorAnonymousCounts() {
        int residentCount = entryRepository.countByHouseIsNullAndEntryType_IdNot("ANON");
        int visitorCount = entryRepository.countByHouseIsNotNull();
        int anonymousCount = entryRepository.countByEntryType_Id("ANON");

        EntryTypeCountDTO dto = new EntryTypeCountDTO();
        dto.setData(Arrays.asList(residentCount, visitorCount, anonymousCount));
        return dto;
    }

    @Override
    public CombinedEntryTypeCountDTO getCombinedCounts() {
        EntryTypeCountDTO graph1Counts = getVehicleAndPedestrianCounts();
        EntryTypeCountDTO graph2Counts = getResidentVisitorAnonymousCounts();
        EntryWeekdayCountDTO graph3Counts = getWeekdayCounts();

        CombinedEntryTypeCountDTO combinedCounts = new CombinedEntryTypeCountDTO();
        combinedCounts.setGraph1Counts(graph1Counts.getData());
        combinedCounts.setGraph2Counts(graph2Counts.getData());
        combinedCounts.setGraph3Counts(graph3Counts.getData());

        return combinedCounts;
    }

    @Override
    public EntryWeekdayCountDTO getWeekdayCounts() {
        LocalDate now = LocalDate.now();
        List<Entry> entries = entryRepository.findAll().stream()
                .filter(entry -> entry.getDate() != null && entry.getDate().getMonth() == now.getMonth())
                .collect(Collectors.toList());

        Map<DayOfWeek, Long> counts = entries.stream()
                .collect(Collectors.groupingBy(entry -> entry.getDate().getDayOfWeek(), Collectors.counting()));

        EntryWeekdayCountDTO dto = new EntryWeekdayCountDTO();
        dto.setData(Arrays.asList(
                counts.getOrDefault(DayOfWeek.MONDAY, 0L).intValue(),
                counts.getOrDefault(DayOfWeek.TUESDAY, 0L).intValue(),
                counts.getOrDefault(DayOfWeek.WEDNESDAY, 0L).intValue(),
                counts.getOrDefault(DayOfWeek.THURSDAY, 0L).intValue(),
                counts.getOrDefault(DayOfWeek.FRIDAY, 0L).intValue(),
                counts.getOrDefault(DayOfWeek.SATURDAY, 0L).intValue(),
                counts.getOrDefault(DayOfWeek.SUNDAY, 0L).intValue()
        ));

        return dto;
    }

    private EntryDTO toDTO(Entry entry) {
        EntryDTO dto = new EntryDTO();
        dto.setId(entry.getId());
        dto.setDate(entry.getDate());
        dto.setEntryTime(entry.getEntryTime());
        dto.setUserName(entry.getUser() != null ? entry.getUser().getName() : null);
        dto.setHouseAddress(entry.getHouse() != null ? entry.getHouse().getAddress() : null);
        dto.setDui(entry.getDui());
        dto.setComment(entry.getComment());
        dto.setEntryType(entry.getEntryType() != null ? entry.getEntryType().getType() : null);
        dto.setHeadline(entry.getHeadline());
        return dto;
    }

    private EntryWithHouseNumberDTO toEntryWithHouseNumberDTO(Entry entry) {
        EntryWithHouseNumberDTO dto = new EntryWithHouseNumberDTO();
        dto.setId(entry.getId());
        dto.setDate(entry.getDate());
        dto.setEntryTime(entry.getEntryTime());
        dto.setUserName(entry.getUser() != null ? entry.getUser().getName() : null);
        dto.setHouseAddress(entry.getHouse() != null ? entry.getHouse().getAddress() : null);
        dto.setHouseNumber(entry.getHouse() != null ? entry.getHouse().getHouseNumber() : null);
        dto.setDui(entry.getDui());
        dto.setComment(entry.getComment());
        dto.setEntryType(entry.getEntryType() != null ? entry.getEntryType().getType() : null);
        dto.setHeadline(entry.getHeadline());

        if (dto.getHouseAddress() == null || dto.getHouseNumber() == null) {
            if (entry.getUser() != null) {
                User user = userRepository.findById(entry.getUser().getId())
                        .orElse(null);
                if (user != null && user.getHouse() != null) {
                    dto.setHouseAddress(user.getHouse().getAddress());
                    dto.setHouseNumber(user.getHouse().getHouseNumber());
                }
            }
        }

        return dto;
    }

}
