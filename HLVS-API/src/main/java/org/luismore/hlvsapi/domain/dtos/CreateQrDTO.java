package org.luismore.hlvsapi.domain.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateQrDTO {
    @NotBlank
    private String token;
    @NotNull
    private Integer duration;
    @NotNull
    private UUID requestId;
}

