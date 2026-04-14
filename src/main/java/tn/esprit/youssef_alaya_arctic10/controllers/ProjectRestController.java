package tn.esprit.youssef_alaya_arctic10.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.youssef_alaya_arctic10.entities.Calls;
import tn.esprit.youssef_alaya_arctic10.entities.Project;
import tn.esprit.youssef_alaya_arctic10.services.IProjectServices;
import tn.esprit.youssef_alaya_arctic10.dto.ProjectsDTO;
import java.util.List;

@RequestMapping("project")
@RestController
@RequiredArgsConstructor
public class ProjectRestController {
    private final IProjectServices projectServices;

    @PostMapping("add")
    public void addProject(@RequestBody Project project){
        projectServices.addProject(project);
    }

    @PutMapping("update")
    public void updateProject(@RequestBody Project project) {
        projectServices.updateProject(project);
    }

    @DeleteMapping("delete/{id}")
    public void deleteProjectById(@PathVariable long id) {
        projectServices.deleteProjectById(id);
    }

    public List<Project> getAll() {
        return projectServices.getAll();
    }

    @GetMapping("get/{id}")
    public Project getById(@PathVariable long id) {
        return projectServices.getProjectById(id);
    }

    @PutMapping("assignToAgent/{projectId}/{agentId}")
    public Project assignToAgent(@PathVariable long projectId, @PathVariable long agentId) {
        return projectServices.assignToAgent(projectId, agentId);
    }

    @GetMapping("findProject/{id}")
    public ProjectsDTO getProjectDTOById(@PathVariable long id) {
        return projectServices.findProjectDTO(id);
    }


}
