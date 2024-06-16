package org.luismore.hlvsapi.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
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
    private Date entryDate;
    private Date entryTime;
    private Date limitTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_house")
    private House house;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_state")
    private State state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_day")
    private Day day;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_visitor")
    private User visitor;

    @ManyToMany
    @JoinTable(
            name = "user_request",
            joinColumns = @JoinColumn(name = "id_request"),
            inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private List<User> users;
}
