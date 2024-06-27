package org.luismore.hlvsapi.services;

import org.luismore.hlvsapi.domain.dtos.EntryAnonymousDTO;
import org.luismore.hlvsapi.domain.dtos.EntryDTO;

import java.util.List;

public interface EntryService {
    List<EntryDTO> getAllEntries();
    List<EntryDTO> getFilteredEntries(String type);
    EntryDTO registerEntry(EntryDTO entryDTO);
    EntryDTO registerAnonymousEntry(EntryAnonymousDTO info);
}
