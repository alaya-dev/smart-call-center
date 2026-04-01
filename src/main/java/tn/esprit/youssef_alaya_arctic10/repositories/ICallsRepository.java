package tn.esprit.youssef_alaya_arctic10.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.youssef_alaya_arctic10.entities.AISystem;
import tn.esprit.youssef_alaya_arctic10.entities.Calls;
import tn.esprit.youssef_alaya_arctic10.entities.*;
import java.util.List;

public interface ICallsRepository extends JpaRepository<Calls, Long> {
    long countByAssignedAiSystem(AISystem aiSystem);
    List<Calls> findByStatus(CallStatus status);
    List<Calls> findByStatusAndAssignedAgent_AgentsId(CallStatus status, long agentId);
    List<Calls> findByAssignedAgentIsNull();
    List<Calls> findByRequiredSkillsContains(CallSkills skill);
    List<Calls> findTop5ByOrderByCallsDateTimeAscAndRequiredSkillsIn(CallSkills skill);
    boolean existsByPhoneNumber(String phoneNumber);
    long countByStatus(CallStatus status);

}
