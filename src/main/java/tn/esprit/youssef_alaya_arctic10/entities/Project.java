package tn.esprit.youssef_alaya_arctic10.entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long projectsId;
    String libelle;
    LocalDate startDate;
    LocalDate endDate;

//    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    ProjectDetails projectDetails;

    @ManyToMany(fetch = FetchType.EAGER)
    Set<Agent> agents = new HashSet<>();

}