package tn.esprit.youssef_alaya_arctic10.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.youssef_alaya_arctic10.entities.Calls;
import tn.esprit.youssef_alaya_arctic10.entities.ProjectDetails;

import java.util.List;

public interface IProjectDetailsRepository extends JpaRepository<ProjectDetails, Long> {
    @Query("SELECT c FROM Calls c " +
            "JOIN c.assignedAgent agent" +
            " JOIN agent.projects project" +
            " WHERE project.libelle = :libelle")
    List<Calls> getCallsByProjectLibelle(@Param("libelle") String projectLibelle);

    @Query("""
            SELECT SUM(pd.budget)
            FROM ProjectDetails pd
            WHERE pd = (
                SELECT p.projectDetails
                FROM Agent a
                JOIN a.projects p
                WHERE a.name = :agentName
            )
            """)
    double sumOfBudgetByAgentName(String agentName);

}
