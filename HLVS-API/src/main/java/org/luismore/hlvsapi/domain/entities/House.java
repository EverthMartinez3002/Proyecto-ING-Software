package org.luismore.hlvsapi.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "house")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String houseNumber;
    private String address;
    private String residentNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_leader")
    private User leader;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> residents;

    @OneToMany(mappedBy = "house", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Request> requests;

}
