package org.luismore.hlvs.services.impl;

import org.luismore.hlvs.dtos.EntryDto;
import org.luismore.hlvs.entities.Entry;
import org.luismore.hlvs.repositories.EntryRepository;
import org.luismore.hlvs.services.EntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryServiceImpl implements EntryService {

    @Autowired
    private EntryRepository entryRepository;

    @Override
    public Entry registerEntry(EntryDto entryDto) {
        Entry entry = new Entry();
        // ENTRYDTO
        entry.setType(entryDto.getType());
        entry.setResident(entryDto.getResident());
        entry.setVehicle(entryDto.getVehicle());
        entry.setEntryTime(entryDto.getEntryTime());
        return entryRepository.save(entry);
    }

    @Override
    public Entry registerAnonymousEntry(EntryDto entryDto) {
        Entry entry = new Entry();
        // ENTRYDTO
        entry.setType("Anonymous");
        entry.setComment(entryDto.getComment());
        entry.setHeadline(entryDto.getHeadline());
        return entryRepository.save(entry);
    }

    @Override
    public List<Entry> viewAllEntries() {
        return entryRepository.findAll();
    }
}
