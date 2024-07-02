package org.luismore.hlvsapi.controllers;

import org.luismore.hlvsapi.domain.dtos.*;
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

import java.util.HashMap;
import java.util.Map;

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

    @GetMapping("/counts/graph1")
    public ResponseEntity<GeneralResponse> getVehicleAndPedestrianCounts() {
        EntryTypeCountDTO counts = entryService.getVehicleAndPedestrianCounts();
        return GeneralResponse.getResponse(HttpStatus.OK, counts);
    }

    @GetMapping("/counts/graph2")
    public ResponseEntity<GeneralResponse> getResidentVisitorAnonymousCounts() {
        EntryTypeCountDTO counts = entryService.getResidentVisitorAnonymousCounts();
        return GeneralResponse.getResponse(HttpStatus.OK, counts);
    }

    @GetMapping("/counts/combined")
    public ResponseEntity<GeneralResponse> getCombinedCounts() {
        CombinedEntryTypeCountDTO counts = entryService.getCombinedCounts();
        return GeneralResponse.getResponse(HttpStatus.OK, counts);
    }

}
