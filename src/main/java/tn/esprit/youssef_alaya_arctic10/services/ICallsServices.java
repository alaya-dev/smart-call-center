package tn.esprit.youssef_alaya_arctic10.services;

import tn.esprit.youssef_alaya_arctic10.entities.Calls;
import tn.esprit.youssef_alaya_arctic10.entities.CallSkills;
import tn.esprit.youssef_alaya_arctic10.entities.CallStatus;

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
    List<Calls> findByStatusAndAssignedAgent_AgentsId(CallStatus status, long agentId);
    List<Calls> findByStatus(CallStatus status);
    List<Calls> findByAssignedAgentIsNull();
    List<Calls> findByRequiredSkillsContains(CallSkills skill);
    List<Calls> findTop5ByOrderByCallsDateTimeAscAndRequiredSkillsIn(CallSkills skill);
    boolean existsByPhoneNumber(String phoneNumber);
    long countByStatus(CallStatus status);

    List<Calls> getCallsByProjectLibelle(String libelle);

}
