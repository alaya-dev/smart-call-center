package tn.esprit.youssef_alaya_arctic10.services;

import tn.esprit.youssef_alaya_arctic10.entities.ProjectDetails;

import java.util.List;

public interface IProjectDetailsService {
    ProjectDetails addProjectDetails(ProjectDetails projectDetails);
    ProjectDetails updateProjectDetails(ProjectDetails projectDetails);
    void deleteById(long id);
    void deleteProjectDetails(ProjectDetails projectDetails);
    ProjectDetails getById(long id);
    List<ProjectDetails> getAll();
}
