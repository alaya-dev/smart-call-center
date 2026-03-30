package tn.esprit.youssef_alaya_arctic10.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long agentsId;

    String name;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    Set<CallSkills> skills = new HashSet<>();

    boolean available;

    @OneToMany(mappedBy = "assignedAgent")
    Set<Calls> calls ;

    @ManyToOne
    AISystem system;

    @ManyToMany(mappedBy = "agents")
    Set<Project> projects = new HashSet<>();
}

