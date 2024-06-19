//package org.luismore.hlvsapi.services;
//
//import org.luismore.hlvsapi.domain.dtos.CreateDeviceDTO;
//import org.luismore.hlvsapi.domain.dtos.UpdateDeviceDTO;
//import org.luismore.hlvsapi.domain.dtos.UpdateDeviceLocationDTO;
//import org.luismore.hlvsapi.domain.entities.Tablet;
//import org.luismore.hlvsapi.domain.entities.User;
//
//import java.util.List;
//
////public interface DeviceService {
////    List<Tablet> getAllDevices();
////    void createDevice(CreateDeviceDTO createDeviceDTO);
////    void updateDevice(UpdateDeviceDTO updateDeviceDTO);
////    void updateDeviceLocation(String serialNumber, String location);
////}
//
//public interface DeviceService {
//    List<Tablet> getAllDevices();
//    void createDevice(CreateDeviceDTO createDeviceDTO, User securityGuard);
//    void updateDevice(String serialNumber, UpdateDeviceDTO updateDeviceDTO, User securityGuard);
//    List<Tablet> getDevicesByGuardEmail(String email);
//}
//

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
