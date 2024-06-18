package org.luismore.hlvsapi.services;

import org.luismore.hlvsapi.domain.dtos.CreateAnonymousEntryDTO;
import org.luismore.hlvsapi.domain.dtos.CreateEntryDTO;
import org.luismore.hlvsapi.domain.dtos.EntryDTO;

import java.util.List;

public interface EntryService {
    void registerEntry(CreateEntryDTO createEntryDTO);
    void registerAnonymousEntry(CreateAnonymousEntryDTO createAnonymousEntryDTO);
    List<EntryDTO> getAllEntries();
}
