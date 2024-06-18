package org.luismore.hlvsapi.services.impls;

import jakarta.transaction.Transactional;
import org.luismore.hlvsapi.domain.dtos.CreateAnonymousEntryDTO;
import org.luismore.hlvsapi.domain.dtos.CreateEntryDTO;
import org.luismore.hlvsapi.domain.dtos.EntryDTO;
import org.luismore.hlvsapi.domain.entities.Entry;
import org.luismore.hlvsapi.domain.entities.EntryType;
import org.luismore.hlvsapi.domain.entities.House;
import org.luismore.hlvsapi.domain.entities.User;
import org.luismore.hlvsapi.repositories.EntryRepository;
import org.luismore.hlvsapi.repositories.EntryTypeRepository;
import org.luismore.hlvsapi.repositories.HouseRepository;
import org.luismore.hlvsapi.repositories.UserRepository;
import org.luismore.hlvsapi.services.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntryServiceImpl implements EntryService {

    private final EntryRepository entryRepository;
    private final EntryTypeRepository entryTypeRepository;
    private final HouseRepository houseRepository;
    private final UserRepository userRepository;

    @Autowired
    public EntryServiceImpl(EntryRepository entryRepository, EntryTypeRepository entryTypeRepository, HouseRepository houseRepository, UserRepository userRepository) {
        this.entryRepository = entryRepository;
        this.entryTypeRepository = entryTypeRepository;
        this.houseRepository = houseRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void registerEntry(CreateEntryDTO createEntryDTO) {
        Entry entry = new Entry();
        entry.setDate(createEntryDTO.getDate());
        entry.setEntryTime(createEntryDTO.getEntryTime());
        entry.setComment(createEntryDTO.getComment());
        entry.setDui(createEntryDTO.getDui());

        House house = houseRepository.findById(createEntryDTO.getHouseId())
                .orElseThrow(() -> new IllegalArgumentException("House Can(not) be found"));
        User user = userRepository.findById(createEntryDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User Can(not) be found"));

        entry.setHouse(house);
        entry.setUser(user);

        EntryType entryType = entryTypeRepository.findById(createEntryDTO.getEntryTypeId())
                .orElseThrow(() -> new IllegalArgumentException("Entry type Can(not) be found"));
        entry.setEntryType(entryType);

        entryRepository.save(entry);
    }

    @Override
    @Transactional
    public void registerAnonymousEntry(CreateAnonymousEntryDTO createAnonymousEntryDTO) {
        Entry entry = new Entry();
        entry.setDate(createAnonymousEntryDTO.getDate());
        entry.setEntryTime(createAnonymousEntryDTO.getEntryTime());
        entry.setComment(createAnonymousEntryDTO.getComment());
        entry.setHeadline(createAnonymousEntryDTO.getHeadline());
        entryRepository.save(entry);
    }

//    @Override
//    public List<Entry> getAllEntries() {
//        return entryRepository.findAll();
//    }


    @Override
    public List<EntryDTO> getAllEntries() {
        List<Entry> entries = entryRepository.findAll();
        return entries.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private EntryDTO convertToDTO(Entry entry) {
        EntryDTO dto = new EntryDTO();
        dto.setId(entry.getId());
        dto.setDate(entry.getDate());
        dto.setEntryTime(entry.getEntryTime());
        dto.setComment(entry.getComment());
        dto.setDui(entry.getDui());
        dto.setHeadline(entry.getHeadline());
        dto.setEntryTypeId(entry.getEntryType() != null ? entry.getEntryType().getId() : null);
        dto.setHouseId(entry.getHouse() != null ? entry.getHouse().getId() : null);
        dto.setUserId(entry.getUser() != null ? entry.getUser().getId() : null);
        return dto;
    }

}
