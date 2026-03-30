package tn.esprit.youssef_alaya_arctic10.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.youssef_alaya_arctic10.entities.ProjectDetails;
import tn.esprit.youssef_alaya_arctic10.repositories.IProjectDetailsRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectDetailsServicesImpl implements IProjectDetailsService{
    private final IProjectDetailsRepository projectDetailsRepository;

    @Override
    public ProjectDetails addProjectDetails(ProjectDetails projectDetails) {
        return projectDetailsRepository.save(projectDetails);
    }

    @Override
    public ProjectDetails updateProjectDetails(ProjectDetails projectDetails) {
        return projectDetailsRepository.save(projectDetails);
    }

    @Override
    public void deleteById(long id) {
        projectDetailsRepository.deleteById(id);
    }

    @Override
    public void deleteProjectDetails(ProjectDetails projectDetails) {
        projectDetailsRepository.delete(projectDetails);
    }

    @Override
    public ProjectDetails getById(long id) {
        return projectDetailsRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Calls with id " + id + " not found"));
    }

    @Override
    public List<ProjectDetails> getAll() {
        return projectDetailsRepository.findAll();
    }
}
