package org.luismore.hlvsapi.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateDeviceDTO {
    @NotNull
    private UUID id;
    @NotBlank
    private String serialNumber;
    @NotBlank
    private String location;
}
