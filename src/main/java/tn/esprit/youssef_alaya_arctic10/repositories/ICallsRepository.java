package tn.esprit.youssef_alaya_arctic10.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.youssef_alaya_arctic10.entities.*;
import java.util.List;

public interface ICallsRepository extends JpaRepository<Calls, Long> {

    long countByAssignedAiSystem(AISystem aiSystem);
    boolean existsByPhoneNumber(String phoneNumber);
    long countByStatus(CallStatus status);

    @Query("SELECT c FROM Calls c WHERE c.assignedAgent.agentsId = :idAgent")
    List<Calls> findCallsByAgent(@Param("idAgent") Long idAgent);

    @Query("SELECT c FROM Calls c WHERE :skill MEMBER OF c.requiredSkills")
    List<Calls> findCallsBySkill(@Param("skill") CallSkills skill);

    @Query("SELECT c FROM Calls c WHERE c.status = :status")
    List<Calls> findByStatus(@Param("status") CallStatus status);

    @Query("SELECT c FROM Calls c WHERE c.assignedAgent.agentsId = :agentId AND c.status = :status")
    List<Calls> findByStatusAndAssignedAgent_AgentsId(@Param("status") CallStatus status, @Param("agentId") long agentId);

    @Query("SELECT c FROM Calls c WHERE c.assignedAgent IS NULL")
    List<Calls> findByAssignedAgentIsNull();

    @Query("SELECT c FROM Calls c WHERE :skill MEMBER OF c.requiredSkills")
    List<Calls> findByRequiredSkillsContains(@Param("skill") CallSkills skill);

    @Query("SELECT c FROM Calls c WHERE :skill MEMBER OF c.requiredSkills ORDER BY c.callsDateTime ASC")
    List<Calls> findTop5ByRequiredSkillsContainsOrderByCallsDateTimeAsc(@Param("skill") CallSkills skill);

    @Query("SELECT c.status, COUNT(c) FROM Calls c GROUP BY c.status")
    List<Object[]> countCallsByStatus();

    @Query("SELECT c FROM Calls c WHERE FUNCTION('DATE', c.callsDateTime) = CURRENT_DATE")
    List<Calls> findTodayCalls();

    @Query("SELECT c FROM Calls c " +
            "JOIN c.assignedAgent agent" +
            " JOIN agent.projects project" +
            " WHERE project.libelle = :libelle")
    List<Calls> getCallsByProjectLibelle(@Param("libelle") String projectLibelle);

    @Query("DELETE FROM Calls c " +
            "WHERE c.status = :status " +
            "AND c.assignedAgent IS NULL " +
            "AND c.assignedAiSystem IS NULL")
    void deleteOldCalls(@Param("status") CallStatus status);
}