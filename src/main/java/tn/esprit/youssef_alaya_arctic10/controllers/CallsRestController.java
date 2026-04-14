package tn.esprit.youssef_alaya_arctic10.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.youssef_alaya_arctic10.entities.Calls;
import tn.esprit.youssef_alaya_arctic10.services.ICallsServices;
import tn.esprit.youssef_alaya_arctic10.entities.CallSkills;
import tn.esprit.youssef_alaya_arctic10.entities.CallStatus;


import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("calls")
public class CallsRestController {
    private final ICallsServices callsServices;
    @PostMapping("add")
    public Calls addCalls(@RequestBody Calls call) {
        return callsServices.addCall(call);
    }
    @PutMapping("update")
    public Calls updateCalls(@RequestBody Calls call) {
        return callsServices.updateCall(call);
    }
    @DeleteMapping("delete/{id}")
    public void deleteCallsById(@PathVariable long id) {
        callsServices.deleteById(id);
    }
    @GetMapping("get/{id}")
    public Calls getById(@PathVariable long id) {
        return callsServices.getById(id);
    }
    @GetMapping("get")
    public List<Calls> getAllCalls() {
        return callsServices.getAll();
    }
    @PutMapping("assignToAgent/{callId}/{agentId}")
    public Calls assignedToAgent(@PathVariable long callId, @PathVariable long agentId) {
        return callsServices.assignedToAgent(callId, agentId);
    }
    @PostMapping("addAndAssignToAgent/{agentId}")
    Calls assignedToAgent (@RequestBody Calls call, @PathVariable long agentId){
        return callsServices.assignedToAgent(call, agentId);
    }

    @PutMapping("assignToAISystem/{callId}/{aiSystemId}")
    public Calls assignedToAISystem(@PathVariable long callId, @PathVariable long aiSystemId) {
        return callsServices.assignedToAISystem(callId, aiSystemId);
    }

    @GetMapping("callRequiresHumanAgent/{callId}")
    public boolean callRequiresHumanAgent(@PathVariable long callId) {
        Calls call = callsServices.getById(callId);
        return callsServices.callRequiresHumanAgent(call);
    }

    @PutMapping("autoAssign")
    public void autoAssignCallsToAgents(@RequestBody Set<Long> callIds) {
        callsServices.autoAssignCallsToAgents(callIds);
    }
    @GetMapping("findByStatusAndAgentId/{status}/{agentId}")
    List<Calls> findByStatusAndAssignedAgent_AgentsId(@PathVariable CallStatus status, @PathVariable long agentId) {
        return callsServices.findByStatusAndAssignedAgent_AgentsId(status, agentId);
    }

    @GetMapping("findByStatus/{status}")
    List<Calls> findByStatus(@PathVariable CallStatus status) {
        return callsServices.findByStatus(status);
    }

    @GetMapping("findUnassigned")
    List<Calls> findByAssignedAgentIsNull() {
        return callsServices.findByAssignedAgentIsNull();
    }

    @GetMapping("findByRequiredSkills/{skill}")
    List<Calls> findByRequiredSkillsContains(@PathVariable CallSkills skill) {
        return callsServices.findByRequiredSkillsContains(skill);
    }

    @GetMapping("getTop5ByCallsDateTimeAndRequiredSkillsIn/{skill}")
    List<Calls> findTop5ByOrderByCallsDateTimeAscAndRequiredSkillsIn(@PathVariable CallSkills skill) {
        return callsServices.findTop5ByOrderByCallsDateTimeAscAndRequiredSkillsIn(skill);
    }

    @GetMapping("existsByPhoneNumber/{phoneNumber}")
    boolean existsByPhoneNumber(@PathVariable String phoneNumber) {
        return callsServices.existsByPhoneNumber(phoneNumber);
    }

    @GetMapping("countByStatus/{status}")
    long countByStatus(@PathVariable CallStatus status) {
        return callsServices.countByStatus(status);
    }

    @PutMapping("assignCallsToAgents")
    public void assignCallsToAgents(@RequestBody Set<Long> callIds) {
        callsServices.assignCallsToAgents(callIds);
    }

    @GetMapping("/project/{libelle}")
    public List<Calls> getCallsByProject(@PathVariable String libelle) {
        return callsServices.getCallsByProjectLibelle(libelle);
    }

}