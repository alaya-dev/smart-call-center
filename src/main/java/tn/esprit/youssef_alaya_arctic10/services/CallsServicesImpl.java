package tn.esprit.youssef_alaya_arctic10.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.youssef_alaya_arctic10.entities.AISystem;
import tn.esprit.youssef_alaya_arctic10.entities.Agent;
import tn.esprit.youssef_alaya_arctic10.entities.CallSkills;
import tn.esprit.youssef_alaya_arctic10.entities.CallStatus;
import tn.esprit.youssef_alaya_arctic10.entities.Calls;
import tn.esprit.youssef_alaya_arctic10.repositories.IAISystemRepository;
import tn.esprit.youssef_alaya_arctic10.repositories.IAgentRepository;
import tn.esprit.youssef_alaya_arctic10.repositories.ICallsRepository;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class CallsServicesImpl implements ICallsServices{

    private final ICallsRepository callsRepository;
    private final IAgentRepository agentRepository;
    private  final IAISystemRepository aiSystemRepository;

//    @Autowired
//    public void setCallsRepository(ICallsRepository callsRepository) {
//        this.callsRepository = callsRepository;
//    }

    @Override
    public Calls addCall(Calls call) {
        call.setCallsDateTime(java.time.LocalDateTime.now());
        call.setStatus(CallStatus.ON_HOLD);
        return callsRepository.save(call);
    }

    @Override
    public Calls updateCall(Calls call) {
        return callsRepository.save(call);
    }

    @Override
    public void deleteById(long id) {
        callsRepository.deleteById(id);
    }

    @Override
    public Calls getById(long id) {
        return callsRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Calls with id " + id + " not found"));
    }

    @Override
    public List<Calls> getAll() {
        return callsRepository.findAll();
    }

    @Override
    public Calls assignedToAgent(long callId, long agentId) {
        Calls call = callsRepository.findById(callId).orElseThrow(()->new EntityNotFoundException("call with the id "+callId+" not found"));
        Agent agent = agentRepository.findById(agentId).orElseThrow(()->new EntityNotFoundException("agent with the id "+agentId+" not found"));
        if(!agent.isAvailable()){
            throw new IllegalStateException("agent with the id "+agentId+" is not available");
        }
        if ( agent != null && agent.isAvailable()) {
            call.setAssignedAgent(agent); //affectation
            agent.setAvailable(false); //agent n'est plus disponible
            call.setStatus(CallStatus.IN_PROGRESS); //le status de l'appel devient en cours
            agentRepository.save(agent); //sauvegarder les modifications de l'agent
        }

        return callsRepository.save(call);
    }

    @Override
    public Calls assignedToAgent(Calls call, long agentId) {
            Agent agent = agentRepository.findById(agentId).orElseThrow(()->new EntityNotFoundException("agent with the id "+agentId+" not found"));
            call.setAssignedAgent(agent);
        return callsRepository.save(call);
    }

    @Override
    public Calls assignedToAISystem(long callId, long aiSystemId) {
        Calls call = callsRepository.findById(callId).orElseThrow(()->new EntityNotFoundException("call with the id "+callId+" not found"));
        AISystem aiSystem = aiSystemRepository.findById(aiSystemId).orElseThrow(()->new EntityNotFoundException("AI system with the id "+aiSystemId+" not found"));
        if(!aiSystem.isAvailable()){
            throw new IllegalStateException("AI system with the id "+aiSystemId+" is not available");
        }
        long currentCalls = callsRepository.countByAssignedAiSystem(aiSystem);
        if(currentCalls >= 2){
            throw new IllegalStateException("AI system with the id "+aiSystemId+" already handles 2 calls");
        }
        call.setAssignedAiSystem(aiSystem); //affectation
        // Si après cette affectation l'IA atteint 2 appels, elle n'est plus disponible
        if(currentCalls + 1 >= 2){
            aiSystem.setAvailable(false);
            aiSystemRepository.save(aiSystem);
        }
        return callsRepository.save(call);
    }

    @Override
    public boolean callRequiresHumanAgent(Calls call) {
        return call.getRequiredSkills() != null && call.getRequiredSkills().contains(CallSkills.Technical_Support);
    }

    @Override
    public void autoAssignCallsToAgents(Set<Long> callIds) {
        for (Long callId : callIds) {
            Calls call = callsRepository.findById(callId) .orElseThrow(() -> new EntityNotFoundException("Call with id " + callId + " not found"));
            // Vérifier si l'appel nécessite un agent humain
            if (!callRequiresHumanAgent(call)) {
                continue;
            }
            // Trouver un agent disponible possédant au moins une compétence requise
            List<Agent> agents = agentRepository.findAll();
            Agent matchedAgent = null;
            for (Agent agent : agents) {
                if (agent.isAvailable() && agent.getSkills() != null) {
                    for (CallSkills skill : call.getRequiredSkills()) {
                        if (agent.getSkills().contains(skill)) {
                            matchedAgent = agent;
                            break;
                        }
                    }
                }
                if (matchedAgent != null) {
                    break;
                }
            }
            if (matchedAgent != null) {
                call.setAssignedAgent(matchedAgent);
                call.setStatus(CallStatus.IN_PROGRESS);
                matchedAgent.setAvailable(false);
                agentRepository.save(matchedAgent);
                callsRepository.save(call);
            }
        }
    }

    @Override
    public void assignCallsToAgents(Set<Long> callIds) {
        for (Long callId : callIds) {
            Calls call = callsRepository.findById(callId) .orElseThrow(() -> new EntityNotFoundException("Call with id " + callId + " not found"));
            // Si l'appel ne requiert pas une intervention humaine, on l'ignore
            if (!callRequiresHumanAgent(call)) {
                continue;
            }
            // Trouver un agent disponible possédant au moins une compétence requise
            List<Agent> agents = agentRepository.findAll();
            Agent matchedAgent = null;
            for (Agent agent : agents) {
                if (agent.isAvailable() && agent.getSkills() != null) {
                    for (CallSkills skill : call.getRequiredSkills()) {
                        if (agent.getSkills().contains(skill)) {
                            matchedAgent = agent;
                            break;
                        }
                    }
                }
                if (matchedAgent != null) {
                    break;
                }
            }
            // Affecter l'agent s'il existe, sinon l'appel reste non assigné
            if (matchedAgent != null) {
                call.setAssignedAgent(matchedAgent);
                call.setStatus(CallStatus.IN_PROGRESS);
                matchedAgent.setAvailable(false);
                agentRepository.save(matchedAgent);
                callsRepository.save(call);
            }
        }
    }

    @Override
    public List<Calls> findByStatusAndAssignedAgent_AgentsId(CallStatus status, long agentId) {
        return callsRepository.findByStatusAndAssignedAgent_AgentsId(status, agentId);
    }

    @Override
    public List<Calls> findByStatus(CallStatus status) {
        return callsRepository.findByStatus(status);
    }

    @Override
    public List<Calls> findByAssignedAgentIsNull() {
        return callsRepository.findByAssignedAgentIsNull();
    }

    @Override
    public List<Calls> findByRequiredSkillsContains(CallSkills skill) {
        return callsRepository.findByRequiredSkillsContains(skill);
    }

    @Override
    public List<Calls> findTop5ByOrderByCallsDateTimeAscAndRequiredSkillsIn(CallSkills skill) {
        return callsRepository.findTop5ByOrderByCallsDateTimeAscAndRequiredSkillsIn(skill);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return callsRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public long countByStatus(CallStatus status) {
        return callsRepository.countByStatus(status);
    }

}
