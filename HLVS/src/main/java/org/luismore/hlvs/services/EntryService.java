package org.luismore.hlvs.services;

import org.luismore.hlvs.dtos.EntryDto;
import org.luismore.hlvs.entities.Entry;

import java.util.List;

public interface EntryService {
    Entry registerEntry(EntryDto entryDto);
    Entry registerAnonymousEntry(EntryDto entryDto);
    List<Entry> viewAllEntries();
}
