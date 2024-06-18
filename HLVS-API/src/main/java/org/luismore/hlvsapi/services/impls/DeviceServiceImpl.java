package org.luismore.hlvsapi.services.impls;

import jakarta.transaction.Transactional;
import org.luismore.hlvsapi.domain.dtos.CreateDeviceDTO;
import org.luismore.hlvsapi.domain.dtos.UpdateDeviceDTO;
import org.luismore.hlvsapi.domain.entities.Tablet;
import org.luismore.hlvsapi.repositories.TabletRepository;
import org.luismore.hlvsapi.services.DeviceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final TabletRepository tabletRepository;

    public DeviceServiceImpl(TabletRepository tabletRepository) {
        this.tabletRepository = tabletRepository;
    }

    @Override
    public List<Tablet> getAllDevices() {
        return tabletRepository.findAll();
    }

    @Override
    @Transactional
    public void createDevice(CreateDeviceDTO createDeviceDTO) {
        Tablet tablet = new Tablet();
        tablet.setSerialNumber(createDeviceDTO.getSerialNumber());
        tablet.setLocation(createDeviceDTO.getLocation());
        tabletRepository.save(tablet);
    }

    @Override
    @Transactional
    public void updateDevice(UpdateDeviceDTO updateDeviceDTO) {
        Tablet tablet = tabletRepository.findById(updateDeviceDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Tablet Can(not) be found"));
        tablet.setSerialNumber(updateDeviceDTO.getSerialNumber());
        tablet.setLocation(updateDeviceDTO.getLocation());
        tabletRepository.save(tablet);
    }

    @Override
    @Transactional
    public void updateDeviceLocation(String serialNumber, String location) {
        Tablet tablet = tabletRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new IllegalArgumentException("Tablet Can(not) be found with serial number: " + serialNumber));
        tablet.setLocation(location);
        tabletRepository.save(tablet);
    }
}
