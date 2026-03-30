package tn.esprit.youssef_alaya_arctic10.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.youssef_alaya_arctic10.entities.Agent;
import tn.esprit.youssef_alaya_arctic10.entities.Project;
import tn.esprit.youssef_alaya_arctic10.repositories.IAgentRepository;
import tn.esprit.youssef_alaya_arctic10.repositories.IProjectRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServicesImpl implements IProjectServices{

    private final IProjectRepository projectRepository;
    private final IAgentRepository agentRepository;

    @Override
    public Project addProject(Project project) {
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

}
