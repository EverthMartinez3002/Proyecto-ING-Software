package org.luismore.controllers;

import org.luismore.services.DevicesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DevicesController {

    private final DeviceService deviceService;

    public DevicesController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/all")
    public ResponseEntity<GeneralResponse> getAllDevices() {
        List<Device> devices = deviceService.findAll();
        return GeneralResponse.getResponse(HttpStatus.OK, devices);
    }

    @PostMapping("/create")
    public ResponseEntity<GeneralResponse> createDevice(@RequestBody @Valid DeviceDTO deviceDTO) {
        deviceService.createDevice(deviceDTO);
        return GeneralResponse.getResponse(HttpStatus.CREATED, "Device created successfully");
    }

    @PostMapping("/update")
    public ResponseEntity<GeneralResponse> updateDevice(@RequestBody @Valid DeviceDTO deviceDTO) {
        deviceService.updateDevice(deviceDTO);
        return GeneralResponse.getResponse(HttpStatus.OK, "Device updated successfully");
    }
}

