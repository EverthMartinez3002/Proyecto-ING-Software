package org.luismore.hlvsapi.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateHouseDTO {
    @NotBlank
    private String houseNumber;
    @NotBlank
    private String address;
    @NotBlank
    private String residentNumber;
}
