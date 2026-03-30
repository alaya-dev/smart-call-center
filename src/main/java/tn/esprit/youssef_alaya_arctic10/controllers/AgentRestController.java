package tn.esprit.youssef_alaya_arctic10.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.youssef_alaya_arctic10.entities.Agent;
import tn.esprit.youssef_alaya_arctic10.services.IAgentService;

@RequestMapping("agent")
@RestController
@RequiredArgsConstructor
public class AgentRestController {
    private final IAgentService agentService;

    @PostMapping("add")
    public void addAgent(@RequestBody Agent agent) {
        agentService.addAgent(agent);
    }

    @PutMapping("update")
    public void updateAgent(@RequestBody Agent agent) {
        agentService.updateAgent(agent);
    }

    @DeleteMapping("delete/{id}")
    public void deleteAgentById(@PathVariable long id) {
        agentService.deleteById(id);
    }

    @GetMapping("get/{id}")
    public Agent getAgentById(@PathVariable long id) {
        return agentService.getById(id);
    }

    @GetMapping("get")
    public void getAll() {
        agentService.getAll();
    }

    @PostMapping("addAndAssignToProjects")
    public Agent AddAndAssignToProjects(@RequestBody Agent agent) {
        return agentService.AddAndAssignToProjects(agent);
    }
}
