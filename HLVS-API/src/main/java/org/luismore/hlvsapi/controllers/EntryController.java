package org.luismore.hlvsapi.controllers;

import org.luismore.hlvsapi.domain.dtos.EntryAnonymousDTO;
import org.luismore.hlvsapi.domain.dtos.EntryDTO;
import org.luismore.hlvsapi.domain.dtos.GeneralResponse;
import org.luismore.hlvsapi.services.EntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entries")
public class EntryController {

    private final EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @GetMapping
    public ResponseEntity<List<EntryDTO>> getAllEntries() {
        List<EntryDTO> entries = entryService.getAllEntries();
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/filtered")
    public ResponseEntity<List<EntryDTO>> getFilteredEntries(@RequestParam String type) {
        List<EntryDTO> entries = entryService.getFilteredEntries(type);
        return ResponseEntity.ok(entries);
    }

    @PostMapping
    public ResponseEntity<EntryDTO> registerEntry(@RequestBody EntryDTO entryDTO) {
        EntryDTO entry = entryService.registerEntry(entryDTO);
        return ResponseEntity.ok(entry);
    }

    @PostMapping("/anonymous")
    public ResponseEntity<GeneralResponse>  createEntryAnonymous(@RequestBody EntryAnonymousDTO info) {
        entryService.registerAnonymousEntry(info);
        return GeneralResponse.getResponse(HttpStatus.OK, "Anonymous entry created");
    }
}
