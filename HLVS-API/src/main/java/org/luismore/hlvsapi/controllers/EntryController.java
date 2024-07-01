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

    @GetMapping//NO SIRVEEEE
    public ResponseEntity<List<EntryDTO>> getAllEntries() {
        List<EntryDTO> entries = entryService.getAllEntries();
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/filtered")//NO SIRVE
    public ResponseEntity<List<EntryDTO>> getFilteredEntries(@RequestParam String type) {
        List<EntryDTO> entries = entryService.getFilteredEntries(type);
        return ResponseEntity.ok(entries);
    }

    @PostMapping//NO SIRVE
    public ResponseEntity<EntryDTO> registerEntry(@RequestBody EntryDTO entryDTO) {
        EntryDTO entry = entryService.registerEntry(entryDTO);
        return ResponseEntity.ok(entry);
    }

    @PostMapping("/anonymous")// ESTE SI, NO TOCAR
    public ResponseEntity<GeneralResponse>  createEntryAnonymous(@RequestBody EntryAnonymousDTO info) {
        entryService.registerAnonymousEntry(info);
        return GeneralResponse.getResponse(HttpStatus.OK, "Anonymous entry created");
    }
}
