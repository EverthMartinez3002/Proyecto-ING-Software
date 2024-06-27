package org.luismore.hlvsapi.services.impls;

import org.luismore.hlvsapi.domain.dtos.EntryAnonymousDTO;
import org.luismore.hlvsapi.domain.dtos.EntryDTO;
import org.luismore.hlvsapi.domain.entities.Entry;
import org.luismore.hlvsapi.domain.entities.EntryType;
import org.luismore.hlvsapi.repositories.EntryRepository;
import org.luismore.hlvsapi.repositories.EntryTypeRepository;
import org.luismore.hlvsapi.services.EntryService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntryServiceImpl implements EntryService {

    private final EntryRepository entryRepository;
    private final EntryTypeRepository entryTypeRepository;

    public EntryServiceImpl(EntryRepository entryRepository, EntryTypeRepository entryTypeRepository) {
        this.entryRepository = entryRepository;
        this.entryTypeRepository = entryTypeRepository;
    }

    @Override
    public List<EntryDTO> getAllEntries() {
        return entryRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<EntryDTO> getFilteredEntries(String type) {
        return entryRepository.findAll().stream()
                .filter(entry -> entry.getEntryType().getId().equalsIgnoreCase(type))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EntryDTO registerEntry(EntryDTO entryDTO) {
        Entry entry = toEntity(entryDTO);
        entry = entryRepository.save(entry);
        return toDTO(entry);
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


    private Entry toEntity(EntryDTO dto) {
        Entry entry = new Entry();
        entry.setId(dto.getId());
        entry.setDate(dto.getDate());
        entry.setEntryTime(dto.getEntryTime());
        entry.setUser(dto.getUser());
        entry.setHouse(dto.getHouse());
        entry.setDui(dto.getDui());
        entry.setComment(dto.getComment());
        entry.setEntryType(dto.getEntryType());
        entry.setHeadline(dto.getHeadline());
        return entry;
    }

    private EntryDTO toDTO(Entry entry) {
        EntryDTO dto = new EntryDTO();
        dto.setId(entry.getId());
        dto.setDate(entry.getDate());
        dto.setEntryTime(entry.getEntryTime());
        dto.setUser(entry.getUser());
        dto.setHouse(entry.getHouse());
        dto.setDui(entry.getDui());
        dto.setComment(entry.getComment());
        dto.setEntryType(entry.getEntryType());
        dto.setHeadline(entry.getHeadline());
        return dto;
    }
}
