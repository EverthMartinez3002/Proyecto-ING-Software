package org.luismore.hlvsapi.domain.dtos;

import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
public class EntryDTO {
    private UUID id;
    private Date date;
    private Date entryTime;
    private String comment;
    private String dui;
    private String headline;
    private String entryTypeId;
    private UUID houseId;
    private UUID userId;
}
