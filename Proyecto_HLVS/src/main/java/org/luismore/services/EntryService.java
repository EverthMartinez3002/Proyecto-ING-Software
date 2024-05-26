package org.luismore.services;

import java.util.List;

public interface EntryService {
    void registerEntry(EntryDTO entryDTO);
    void registerAnonymousEntry(AnonymousEntryDTO anonymousEntryDTO);
    List<Entry> findAll();
}

