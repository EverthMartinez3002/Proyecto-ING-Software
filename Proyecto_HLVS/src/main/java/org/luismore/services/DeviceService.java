package org.luismore.services;

import java.util.List;

public interface DeviceService {
    List<Device> findAll();
    void createDevice(DeviceDTO deviceDTO);
    void updateDevice(DeviceDTO deviceDTO);
}

