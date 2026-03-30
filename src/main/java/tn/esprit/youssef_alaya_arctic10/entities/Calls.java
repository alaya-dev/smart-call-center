package tn.esprit.youssef_alaya_arctic10.entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
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
public class Calls {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long callsId;

    String phoneNumber;

    LocalDateTime callsDateTime;

    @Enumerated(EnumType.STRING)
    CallStatus status;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    Set<CallSkills> requiredSkills  = new HashSet<>();

    @ManyToOne
    Agent assignedAgent;

    @ManyToOne
    AISystem assignedAiSystem;
}
