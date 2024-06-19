//package org.luismore.hlvsapi.domain.dtos;
//
//import jakarta.validation.constraints.NotBlank;
//import lombok.Data;
//
//@Data
//public class CreateHouseDTO {
//    @NotBlank
//    private String houseNumber;
//    @NotBlank
//    private String address;
//    @NotBlank
//    private String residentNumber;
//}

package org.luismore.hlvsapi.domain.dtos;

import lombok.Data;

@Data
public class CreateHouseDTO {
    private String houseNumber;
    private String address;
    private String residentNumber;
    private String leaderEmail;
}
