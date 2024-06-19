package org.luismore.hlvsapi.controllers;

import jakarta.validation.Valid;
import org.luismore.hlvsapi.domain.dtos.CreateDeviceDTO;
import org.luismore.hlvsapi.domain.dtos.DeviceDTO;
import org.luismore.hlvsapi.domain.dtos.GeneralResponse;
import org.luismore.hlvsapi.domain.dtos.UpdateDeviceDTO;
import org.luismore.hlvsapi.services.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DevicesController {

    private final DeviceService deviceService;

    public DevicesController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public ResponseEntity<GeneralResponse> getAllDevices() {
        List<DeviceDTO> devices = deviceService.getAllDevices();
        return GeneralResponse.getResponse(HttpStatus.OK, devices);
    }

    @PostMapping
    public ResponseEntity<GeneralResponse> createDevice(@RequestBody @Valid CreateDeviceDTO createDeviceDTO) {
        deviceService.createDevice(createDeviceDTO);
        return GeneralResponse.getResponse(HttpStatus.CREATED, "Device created successfully.");
    }

    @PatchMapping("/update/{serialNumber}")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public ResponseEntity<GeneralResponse> updateDeviceLocation(
            @PathVariable String serialNumber,
            @RequestBody @Valid UpdateDeviceDTO updateDeviceDTO) {
        updateDeviceDTO.setSerialNumber(serialNumber);
        deviceService.updateDevice(updateDeviceDTO);
        return GeneralResponse.getResponse(HttpStatus.OK, "Device location updated successfully.");
    }

    @GetMapping("/guard/{email}")
    @PreAuthorize("hasAuthority('ROLE_admin')")
    public ResponseEntity<GeneralResponse> getDevicesByGuardEmail(@PathVariable String email) {
        List<DeviceDTO> devices = deviceService.getDevicesByGuardEmail(email);
        return GeneralResponse.getResponse(HttpStatus.OK, devices);
    }
}
