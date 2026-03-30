package tn.esprit.youssef_alaya_arctic10.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.youssef_alaya_arctic10.entities.ProjectDetails;
import tn.esprit.youssef_alaya_arctic10.services.IProjectDetailsService;

@RequestMapping("project-details")
@RestController
@RequiredArgsConstructor
public class ProjectDetailsRestController {
    private final IProjectDetailsService projectDetailsService;

    @PostMapping("add")
    public void addProjectDetails(@RequestBody ProjectDetails projectDetails) {
        projectDetailsService.addProjectDetails(projectDetails);
    }

    @PutMapping("update")
    public void updateProjectDetails(@RequestBody ProjectDetails projectDetails) {
        projectDetailsService.updateProjectDetails(projectDetails);
    }

    @DeleteMapping("delete/{id}")
    public void deleteProjectDetailsById(@PathVariable long id) {
        projectDetailsService.deleteById(id);
    }

    @GetMapping("get")
    public void getAll() {
        projectDetailsService.getAll();
    }

    @GetMapping("get/{id}")
    public ProjectDetails getById(@PathVariable long id) {
        return projectDetailsService.getById(id);
    }
}
