package tn.esprit.youssef_alaya_arctic10.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.youssef_alaya_arctic10.entities.Agent;

public interface IAgentRepository extends JpaRepository<Agent, Long> {
}
