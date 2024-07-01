//package org.luismore.hlvsapi.services;
//
//import org.luismore.hlvsapi.domain.dtos.EntryAnonymousDTO;
//import org.luismore.hlvsapi.domain.dtos.EntryDTO;
//
//import java.util.List;
//
//public interface    EntryService {
//    List<EntryDTO> getAllEntries();
//    List<EntryDTO> getFilteredEntries(String type);
//    EntryDTO registerEntry(EntryDTO entryDTO);
//    EntryDTO registerAnonymousEntry(EntryAnonymousDTO info);
//}
package org.luismore.hlvsapi.services;

import org.luismore.hlvsapi.domain.dtos.EntryAnonymousDTO;
import org.luismore.hlvsapi.domain.dtos.EntryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EntryService {
    Page<EntryDTO> getAllEntries(String filter, Pageable pageable);
    Page<EntryDTO> getEntriesByHouse(UUID houseId, Pageable pageable);
    Page<EntryDTO> getEntriesByUser(UUID userId, Pageable pageable);
    EntryDTO registerAnonymousEntry(EntryAnonymousDTO info);
}
