//package org.luismore.hlvsapi.controllers;
//
//import org.luismore.hlvsapi.domain.dtos.EntryAnonymousDTO;
//import org.luismore.hlvsapi.domain.dtos.EntryDTO;
//import org.luismore.hlvsapi.domain.dtos.GeneralResponse;
//import org.luismore.hlvsapi.services.EntryService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/entries")
//public class EntryController {
//
//    private final EntryService entryService;
//
//    public EntryController(EntryService entryService) {
//        this.entryService = entryService;
//    }
//
//    @GetMapping//NO SIRVEEEE
//    public ResponseEntity<List<EntryDTO>> getAllEntries() {
//        List<EntryDTO> entries = entryService.getAllEntries();
//        return ResponseEntity.ok(entries);
//    }
//
//    @GetMapping("/filtered")//NO SIRVE
//    public ResponseEntity<List<EntryDTO>> getFilteredEntries(@RequestParam String type) {
//        List<EntryDTO> entries = entryService.getFilteredEntries(type);
//        return ResponseEntity.ok(entries);
//    }
//
//    @PostMapping//NO SIRVE
//    public ResponseEntity<EntryDTO> registerEntry(@RequestBody EntryDTO entryDTO) {
//        EntryDTO entry = entryService.registerEntry(entryDTO);
//        return ResponseEntity.ok(entry);
//    }
//
//    @PostMapping("/anonymous")// ESTE SI, NO TOCAR
//    public ResponseEntity<GeneralResponse>  createEntryAnonymous(@RequestBody EntryAnonymousDTO info) {
//        entryService.registerAnonymousEntry(info);
//        return GeneralResponse.getResponse(HttpStatus.OK, "Anonymous entry created");
//    }
//}


package org.luismore.hlvsapi.controllers;

import org.luismore.hlvsapi.domain.dtos.EntryAnonymousDTO;
import org.luismore.hlvsapi.domain.dtos.EntryDTO;
import org.luismore.hlvsapi.domain.dtos.GeneralResponse;
import org.luismore.hlvsapi.domain.entities.User;
import org.luismore.hlvsapi.services.EntryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/entries")
public class    EntryController {

    private final EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @GetMapping
    public ResponseEntity<GeneralResponse> getAllEntries(
            @RequestParam(name = "filter", required = false) String filter,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(name = "per_page", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<EntryDTO> entries = entryService.getAllEntries(filter, pageable);
        return GeneralResponse.getResponse(HttpStatus.OK, entries);
    }

    @GetMapping("/by-house")
    @PreAuthorize("hasAuthority('ROLE_main resident')")
    public ResponseEntity<GeneralResponse> getAllEntriesByHouse(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(name = "per_page", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<EntryDTO> entries = entryService.getEntriesByHouse(user.getHouse().getId(), pageable);
        return GeneralResponse.getResponse(HttpStatus.OK, entries);
    }

    @GetMapping("/by-user")
    @PreAuthorize("hasAuthority('ROLE_resident') or hasAuthority('ROLE_visitor')")
    public ResponseEntity<GeneralResponse> getAllEntriesByUser(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(name = "per_page", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page - 1, size);
        Page<EntryDTO> entries = entryService.getEntriesByUser(user.getId(), pageable);
        return GeneralResponse.getResponse(HttpStatus.OK, entries);
    }

    @PostMapping("/anonymous")
    public ResponseEntity<GeneralResponse> createEntryAnonymous(@RequestBody EntryAnonymousDTO info) {
        entryService.registerAnonymousEntry(info);
        return GeneralResponse.getResponse(HttpStatus.OK, "Anonymous entry created");
    }
}
