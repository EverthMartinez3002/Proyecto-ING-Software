package org.luismore.hlvsapi.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "day")
public class Day {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String day;
}
