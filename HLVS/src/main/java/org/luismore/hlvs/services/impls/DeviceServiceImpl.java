package org.luismore.hlvs.services.impls;

import org.luismore.hlvs.dtos.DeviceDto;
import org.luismore.hlvs.entities.Device;
import org.luismore.hlvs.repositories.DeviceRepository;
import org.luismore.hlvs.services.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    public List<Device> listAllDevices() {
        return deviceRepository.findAll();
    }

    @Override
    public Device createDevice(DeviceDto deviceDto) {
        Device device = new Device();
        // DEVICEDTO
        device.setName(deviceDto.getName());
        device.setType(deviceDTO.getDeviceType());
        return deviceRepository.save(device);
    }

    @Override
    public Device updateDevice(Long deviceId, DeviceDto deviceDto) {
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        // DEVICEDTO
        device.setName(deviceDto.getName());
        device.setType(deviceDTO.getDeviceType());
        return deviceRepository.save(device);
    }
}

