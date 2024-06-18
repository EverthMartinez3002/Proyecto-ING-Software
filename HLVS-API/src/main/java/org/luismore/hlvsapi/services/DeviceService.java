package org.luismore.hlvsapi.services;

import org.luismore.hlvsapi.domain.dtos.CreateDeviceDTO;
import org.luismore.hlvsapi.domain.dtos.UpdateDeviceDTO;
import org.luismore.hlvsapi.domain.dtos.UpdateDeviceLocationDTO;
import org.luismore.hlvsapi.domain.entities.Tablet;

import java.util.List;

public interface DeviceService {
    List<Tablet> getAllDevices();
    void createDevice(CreateDeviceDTO createDeviceDTO);
    void updateDevice(UpdateDeviceDTO updateDeviceDTO);
    void updateDeviceLocation(String serialNumber, String location);
}
