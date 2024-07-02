//package org.luismore.hlvsapi.services.impls;
//
//import org.luismore.hlvsapi.domain.dtos.EntryAnonymousDTO;
//import org.luismore.hlvsapi.domain.dtos.EntryDTO;
//import org.luismore.hlvsapi.domain.entities.Entry;
//import org.luismore.hlvsapi.domain.entities.EntryType;
//import org.luismore.hlvsapi.repositories.EntryRepository;
//import org.luismore.hlvsapi.repositories.EntryTypeRepository;
//import org.luismore.hlvsapi.services.EntryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.util.List;
//import java.util.UUID;
//import java.util.stream.Collectors;
//
//@Service
//public class EntryServiceImpl implements EntryService {
//
//    private final EntryRepository entryRepository;
//    private final EntryTypeRepository entryTypeRepository;
//
//    @Autowired
//    public EntryServiceImpl(EntryRepository entryRepository, EntryTypeRepository entryTypeRepository) {
//        this.entryRepository = entryRepository;
//        this.entryTypeRepository = entryTypeRepository;
//    }
//
//    @Override
//    public Page<EntryDTO> getAllEntries(String filter, Pageable pageable) {
//        if (filter != null && !filter.isEmpty()) {
//            return entryRepository.findAllByEntryType_Id(filter, pageable).map(this::toDTO);
//        } else {
//            return entryRepository.findAll(pageable).map(this::toDTO);
//        }
//    }
//
//    @Override
//    public Page<EntryDTO> getEntriesByHouse(UUID houseId, Pageable pageable) {
//        return entryRepository.findAllByHouse_Id(houseId, pageable).map(this::toDTO);
//    }
//
//    @Override
//    public Page<EntryDTO> getEntriesByUser(UUID userId, Pageable pageable) {
//        return entryRepository.findAllByUser_Id(userId, pageable).map(this::toDTO);
//    }
//
//    @Override
//    public EntryDTO registerAnonymousEntry(EntryAnonymousDTO info) {
//        Entry entry = new Entry();
//        entry.setEntryTime(LocalTime.now());
//        entry.setDate(LocalDate.now());
//
//        EntryType entryType = entryTypeRepository.findTypeByType("anonymous");
//        entry.setEntryType(entryType);
//
//        entry.setComment(info.getComment());
//        entry.setHeadline(info.getHeadline());
//
//        entryRepository.save(entry);
//        return null;
//    }
//
//    private EntryDTO toDTO(Entry entry) {
//        EntryDTO dto = new EntryDTO();
//        dto.setId(entry.getId());
//        dto.setDate(entry.getDate());
//        dto.setEntryTime(entry.getEntryTime());
//        dto.setUserName(entry.getUser() != null ? entry.getUser().getName() : null);
//        dto.setHouseAddress(entry.getHouse() != null ? entry.getHouse().getAddress() : null);
//        dto.setDui(entry.getDui());
//        dto.setComment(entry.getComment());
//        dto.setEntryType(entry.getEntryType() != null ? entry.getEntryType().getType() : null);
//        dto.setHeadline(entry.getHeadline());
//        return dto;
//    }
//}

package org.luismore.hlvsapi.services.impls;

import org.luismore.hlvsapi.domain.dtos.CombinedEntryTypeCountDTO;
import org.luismore.hlvsapi.domain.dtos.EntryTypeCountDTO;
import org.luismore.hlvsapi.domain.dtos.EntryAnonymousDTO;
import org.luismore.hlvsapi.domain.dtos.EntryDTO;
import org.luismore.hlvsapi.domain.entities.Entry;
import org.luismore.hlvsapi.domain.entities.EntryType;
import org.luismore.hlvsapi.repositories.EntryRepository;
import org.luismore.hlvsapi.repositories.EntryTypeRepository;
import org.luismore.hlvsapi.services.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.UUID;

@Service
public class EntryServiceImpl implements EntryService {

    private final EntryRepository entryRepository;
    private final EntryTypeRepository entryTypeRepository;

    @Autowired
    public EntryServiceImpl(EntryRepository entryRepository, EntryTypeRepository entryTypeRepository) {
        this.entryRepository = entryRepository;
        this.entryTypeRepository = entryTypeRepository;
    }

    @Override
    public Page<EntryDTO> getAllEntries(String filter, Pageable pageable) {
        if (filter != null && !filter.isEmpty()) {
            return entryRepository.findAllByEntryType_Id(filter, pageable).map(this::toDTO);
        } else {
            return entryRepository.findAll(pageable).map(this::toDTO);
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

        CombinedEntryTypeCountDTO combinedCounts = new CombinedEntryTypeCountDTO();
        combinedCounts.setGraph1Counts(graph1Counts.getData());
        combinedCounts.setGraph2Counts(graph2Counts.getData());

        return combinedCounts;
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


}
