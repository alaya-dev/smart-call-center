package tn.esprit.youssef_alaya_arctic10.services;

import tn.esprit.youssef_alaya_arctic10.entities.Agent;
import tn.esprit.youssef_alaya_arctic10.entities.Calls;
import tn.esprit.youssef_alaya_arctic10.entities.Project;
import tn.esprit.youssef_alaya_arctic10.dto.ProjectsDTO;

import java.util.List;

public interface IProjectServices {

    // khedmet l CRUD
    Project addProject(Project project);
    Project updateProject(Project project);
    void deleteProjectById(long id);
    void deleteProject(Project project);
    Project getProjectById(long id);
    List<Project> getAll();

    List<Agent> getAgents(long projectId);
    Project assignToAgent(long projectId, long agentId);

    ProjectsDTO findProjectDTO(long id);
    ProjectsDTO getProjectDTO(Project project);
}
