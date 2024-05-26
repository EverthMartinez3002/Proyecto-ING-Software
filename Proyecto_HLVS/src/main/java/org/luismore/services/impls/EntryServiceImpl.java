package org.luismore.services.impls;

import org.luismore.services.EntryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntryServiceImpl implements EntryService {

    private final EntryRepository entryRepository;

    public EntryServiceImpl(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @Override
    public void registerEntry(EntryDTO entryDTO) {
        Entry entry = new Entry();
        entry.setUserId(entryDTO.getUserId());
        entry.setRequestId(entryDTO.getRequestId());
        entry.setEntryTime(entryDTO.getEntryTime());
        entryRepository.save(entry);
    }

    @Override
    public void registerAnonymousEntry(AnonymousEntryDTO anonymousEntryDTO) {
        Entry entry = new Entry();
        entry.setDui(anonymousEntryDTO.getDui());
        entry.setEntryTime(anonymousEntryDTO.getEntryTime());
        entryRepository.save(entry);
    }

    @Override
    public List<Entry> findAll() {
        return entryRepository.findAll();
    }
}

