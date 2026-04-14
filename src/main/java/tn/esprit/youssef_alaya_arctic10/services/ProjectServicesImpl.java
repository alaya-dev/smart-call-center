package tn.esprit.youssef_alaya_arctic10.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.youssef_alaya_arctic10.entities.Agent;
import tn.esprit.youssef_alaya_arctic10.entities.Project;
import tn.esprit.youssef_alaya_arctic10.repositories.IAgentRepository;
import tn.esprit.youssef_alaya_arctic10.repositories.IProjectDetailsRepository;
import tn.esprit.youssef_alaya_arctic10.repositories.IProjectRepository;
import tn.esprit.youssef_alaya_arctic10.dto.ProjectsDTO;
import tn.esprit.youssef_alaya_arctic10.dto.ProjectMapper;
import java.util.List;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProjectServicesImpl implements IProjectServices{

    private final IProjectRepository projectRepository;
    private final IAgentRepository agentRepository;
    private final IProjectDetailsRepository projectDetailsRepository;
    private final ProjectMapper projectMapper;

    @Override
    public Project addProject(Project project) {
        // Ensure POST /add always creates new rows and never merges detached entities.
        project.setProjectsId(null);
        if (project.getProjectDetails() != null) {
            project.getProjectDetails().setDetailsId(null);
        }
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public void deleteProjectById(long id) {
        projectRepository.deleteById(id);
    }

    @Override
    public void deleteProject(Project project) {
        projectRepository.delete(project);
    }

    @Override
    public Project getProjectById(long id) {
        return projectRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("project with id " + id + " not found"));
    }

    @Override
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    @Override
    public List<Agent> getAgents(long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(()->new EntityNotFoundException("Project with id "+projectId+" not found"));
        return project.getAgents().stream().toList();
    }

    @Override
    public Project assignToAgent(long projectId, long agentId) {
        Project project = projectRepository.findById(projectId).orElseThrow(()->new EntityNotFoundException("Project with id "+projectId+" not found"));
        Agent agent = agentRepository.findById(agentId).orElseThrow(()->new EntityNotFoundException("Agent with id "+agentId+" not found"));
        project.getAgents().add(agent); // affectation du projet à l'agent
        return projectRepository.save(project);
    }

    @Override
    public ProjectsDTO findProjectDTO(long id) {
        Project project = projectRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("project with id " + id + " not found"));
        return projectMapper.toDTO(project);
    }

    @Override
    public ProjectsDTO getProjectDTO(Project project) {
        ProjectsDTO projectsDTO = new ProjectsDTO();
        projectsDTO.setProjectId(project.getProjectsId());
        projectsDTO.setProjectName(project.getLibelle());
        projectsDTO.setClientName(project.getProjectDetails().getClient());
        return projectsDTO;
    }


    @Override
    public double sumOfBudgetByAgentName(String agentName) {
        return projectDetailsRepository.sumOfBudgetByAgentName(agentName);
    }

    @Scheduled(cron = "0 0 0 * * *")
    @Override
    public void sumProjectsByAgents() {

        log.info("Sum Project budget is calculated!");

    }

}