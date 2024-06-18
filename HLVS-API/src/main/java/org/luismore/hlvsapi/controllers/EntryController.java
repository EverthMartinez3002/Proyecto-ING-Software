package org.luismore.hlvsapi.controllers;

import jakarta.validation.Valid;
import org.luismore.hlvsapi.domain.dtos.*;
import org.luismore.hlvsapi.domain.entities.Entry;
import org.luismore.hlvsapi.services.EntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/entries")
public class EntryController {

    private final EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_security guard') or hasAuthority('ROLE_admin')")
    public ResponseEntity<GeneralResponse> registerEntry(@RequestBody @Valid CreateEntryDTO createEntryDTO) {
        entryService.registerEntry(createEntryDTO);
        return GeneralResponse.getResponse(HttpStatus.CREATED, "Entry registered successfully.");
    }


    @PostMapping("/anonymous")
    @PreAuthorize("hasAuthority('ROLE_security guard') or hasAuthority('ROLE_admin')")
    public ResponseEntity<GeneralResponse> registerAnonymousEntry(@RequestBody @Valid CreateAnonymousEntryDTO createAnonymousEntryDTO) {
        entryService.registerAnonymousEntry(createAnonymousEntryDTO);
        return GeneralResponse.getResponse(HttpStatus.CREATED, "Anonymous entry registered successfully.");
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_security guard')")
    public ResponseEntity<GeneralResponse> getAllEntries() {
        List<EntryDTO> entries = entryService.getAllEntries();
        return GeneralResponse.getResponse(HttpStatus.OK, entries);
    }

}
