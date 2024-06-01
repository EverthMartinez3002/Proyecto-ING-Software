package org.luismore.hlvs.services;


import org.luismore.hlvs.dtos.DeviceDto;
import org.luismore.hlvs.entities.Device;

import java.util.List;

public interface DeviceService {
    List<Device> listAllDevices();
    Device createDevice(DeviceDto deviceDto);
    Device updateDevice(Long deviceId, DeviceDto deviceDto);
}

