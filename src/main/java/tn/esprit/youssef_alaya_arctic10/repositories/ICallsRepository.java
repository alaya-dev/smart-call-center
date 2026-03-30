package tn.esprit.youssef_alaya_arctic10.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.youssef_alaya_arctic10.entities.AISystem;
import tn.esprit.youssef_alaya_arctic10.entities.Calls;

public interface ICallsRepository extends JpaRepository<Calls, Long> {
    long countByAssignedAiSystem(AISystem aiSystem);
}
