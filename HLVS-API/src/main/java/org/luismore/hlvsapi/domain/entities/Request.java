package org.luismore.hlvsapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String DUI;
    private LocalDate entryDate;
    private LocalTime entryTime;
    private LocalTime beforeTime;
    private LocalTime afterTime;
    private LocalTime hour1; // Agregado
    private LocalTime hour2; // Agregado

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_house")
    @JsonBackReference
    private House house;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_state")
    @JsonIgnore
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_limittime")
    @JsonIgnore
    private LimitTime limitTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_visitor")
    @JsonIgnore
    private User visitor;

    @ManyToMany
    @JoinTable(
            name = "user_request",
            joinColumns = @JoinColumn(name = "id_request"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    @JsonIgnore
    private List<User> users;
}
