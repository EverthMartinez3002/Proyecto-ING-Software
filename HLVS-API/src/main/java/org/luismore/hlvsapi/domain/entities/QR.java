package org.luismore.hlvsapi.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "qrs")
public class QR {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uniqueID;

    @Column(name = "token")
    private String token;

    @Column(name = "duration")
    private Integer duration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_request")
    private Request request;
}
