package org.luismore.services.impls;

import org.luismore.services.DeviceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceServiceImpl(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Override
    public List<Device> findAll() {
        return deviceRepository.findAll();
    }

    @Override
    public void createDevice(DeviceDTO deviceDTO) {
        Device device = new Device();
        device.setDeviceName(deviceDTO.getDeviceName());
        device.setDeviceType(deviceDTO.getDeviceType());
        deviceRepository.save(device);
    }

    @Override
    public void updateDevice(DeviceDTO deviceDTO) {
        Device device = deviceRepository.findById(deviceDTO.getId()).orElseThrow();
        device.setDeviceName(deviceDTO.getDeviceName());
        device.setDeviceType(deviceDTO.getDeviceType());
        deviceRepository.save(device);
    }
}

