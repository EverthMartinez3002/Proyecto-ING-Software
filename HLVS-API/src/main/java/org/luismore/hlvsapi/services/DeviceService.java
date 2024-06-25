package org.luismore.hlvsapi.services;

import org.luismore.hlvsapi.domain.dtos.CreateDeviceDTO;
import org.luismore.hlvsapi.domain.dtos.DeviceDTO;
import org.luismore.hlvsapi.domain.dtos.UpdateDeviceDTO;

import java.util.List;

public interface DeviceService {
    List<DeviceDTO> getAllDevices();
    void createDevice(CreateDeviceDTO createDeviceDTO);
    void updateDevice(UpdateDeviceDTO updateDeviceDTO);
    List<DeviceDTO> getDevicesByGuardEmail(String email);
}
