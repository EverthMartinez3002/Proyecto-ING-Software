package org.luismore.controllers;


import org.luismore.services.EntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/entries")
public class EntryController {

    private final EntryService entryService;

    public EntryController(EntryService entryService) {
        this.entryService = entryService;
    }

    @PostMapping("/register")
    public ResponseEntity<GeneralResponse> registerEntry(@RequestBody @Valid EntryDTO entryDTO) {
        entryService.registerEntry(entryDTO);
        return GeneralResponse.getResponse(HttpStatus.CREATED, "Entry registered successfully");
    }

    @PostMapping("/registerAnonymous")
    public ResponseEntity<GeneralResponse> registerAnonymousEntry(@RequestBody @Valid AnonymousEntryDTO anonymousEntryDTO) {
        entryService.registerAnonymousEntry(anonymousEntryDTO);
        return GeneralResponse.getResponse(HttpStatus.CREATED, "Anonymous entry registered successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<GeneralResponse> getAllEntries() {
        List<Entry> entries = entryService.findAll();
        return GeneralResponse.getResponse(HttpStatus.OK, entries);
    }
}

