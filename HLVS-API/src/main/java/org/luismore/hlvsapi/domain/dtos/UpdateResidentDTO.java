package org.luismore.hlvsapi.domain.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateResidentDTO {
    @NotNull
    private UUID houseId;
    @NotNull
    private String email;
    private String name;
    private String password;
}
