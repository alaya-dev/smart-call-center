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
public class AISystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long aiSystemsId;

    String type;

    boolean available;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    Set<CallSkills> skills = new HashSet<>();

    @OneToMany(mappedBy = "assignedAiSystem")
    Set<Calls> calls = new HashSet<>();
}
