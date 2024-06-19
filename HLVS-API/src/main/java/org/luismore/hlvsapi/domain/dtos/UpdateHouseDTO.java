//package org.luismore.hlvsapi.domain.dtos;
//
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.NotNull;
//import lombok.Data;
//
//import java.util.UUID;
//
//@Data
//public class UpdateHouseDTO {
//    @NotNull
//    private UUID id;
//    @NotBlank
//    private String address;
//    @NotBlank
//    private String residentNumber;
//}

package org.luismore.hlvsapi.domain.dtos;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class UpdateHouseDTO {
    private UUID id;
    private String houseNumber;
    private String address;
    private String residentNumber;
    private List<UpdateUserDTO> residents;

}
