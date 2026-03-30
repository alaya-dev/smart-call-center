package tn.esprit.youssef_alaya_arctic10.services;

import tn.esprit.youssef_alaya_arctic10.entities.Calls;

import java.util.List;
import java.util.Set;

public interface ICallsServices {
    Calls addCall(Calls call);
    Calls updateCall(Calls call);
    void deleteById(long callId);
    Calls getById(long callId);
    List<Calls> getAll();
    Calls assignedToAgent (long callId, long agentId);
    Calls assignedToAgent (Calls call, long agentId);
    Calls assignedToAISystem (long callId, long aiSystemId);
    boolean callRequiresHumanAgent(Calls call);
    void autoAssignCallsToAgents(Set<Long> callIds);
    void assignCallsToAgents(Set<Long> callIds);
}
