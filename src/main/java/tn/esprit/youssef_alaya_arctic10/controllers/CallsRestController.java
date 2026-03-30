package tn.esprit.youssef_alaya_arctic10.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.youssef_alaya_arctic10.entities.Calls;
import tn.esprit.youssef_alaya_arctic10.services.ICallsServices;

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

    @PutMapping("assignCallsToAgents")
    public void assignCallsToAgents(@RequestBody Set<Long> callIds) {
        callsServices.assignCallsToAgents(callIds);
    }

}