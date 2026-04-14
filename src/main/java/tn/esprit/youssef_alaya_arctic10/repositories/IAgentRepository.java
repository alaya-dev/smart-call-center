package tn.esprit.youssef_alaya_arctic10.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.youssef_alaya_arctic10.entities.*;
import java.util.List;

public interface IAgentRepository extends JpaRepository<Agent, Long> {

    // Q3 - Agents with a given skill
    @Query("SELECT a FROM Agent a WHERE :skill MEMBER OF a.skills")
    List<Agent> findAgentsBySkill(@Param("skill") CallSkills skill);

    // Q4 - Most competent available agent for a given call
    @Query("""
        SELECT a FROM Agent a
        JOIN Calls c ON c.callsId = :callsId
        WHERE a.available = true
        ORDER BY (
            SELECT COUNT(s) FROM Agent a2
            JOIN a2.skills s
            WHERE a2.agentsId = a.agentsId
            AND s MEMBER OF c.requiredSkills
        ) DESC
        LIMIT 1
        """)
    Agent findMostCompetentAgentForCall(@Param("callsId") Long callsId);
    // Q6 - Agents who handled more than 5 calls
    @Query("""
            SELECT a.name, COUNT(c)
            FROM Agent a
            JOIN Calls c ON c.assignedAgent.agentsId = a.agentsId
            GROUP BY a.agentsId, a.name
            HAVING COUNT(c) > 5
            """)
    List<Object[]> findTopActiveAgents();

}