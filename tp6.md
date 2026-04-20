# Atelier 6 : Spring Data JPA - Affectations — Correction

## Q1 : Ajouter les contraintes à la méthode d'affectation d'un appel à un agent

**Méthode :**
```java
public void assignCallToAgent(Long callId, Long agentId) {
    Agent agent = agentRepository.findById(agentId)
        .orElseThrow(() -> new EntityNotFoundException("Agent not found"));
    Calls call = callsRepository.findById(callId)
        .orElseThrow(() -> new EntityNotFoundException("Call not found"));
    if (!agent.isAvailable()) {
        throw new IllegalStateException("Agent is not available");
    }
    call.setStatus(CallStatus.IN_PROGRESS);
    agent.setAvailable(false);
    call.setAgent(agent);
    callsRepository.save(call);
    agentRepository.save(agent);
}
```
**Contraintes appliquées :**
- L’agent doit être disponible.
- L’appel passe à `IN_PROGRESS`.
- L’agent devient indisponible.

---

## Q2 : Affecter un appel à un système IA

**Méthode :**
```java
public void assignCallToAISystem(Long callId, Long aiSystemId) {
    AISystem ai = aiSystemRepository.findById(aiSystemId)
        .orElseThrow(() -> new EntityNotFoundException("AI System not found"));
    Calls call = callsRepository.findById(callId)
        .orElseThrow(() -> new EntityNotFoundException("Call not found"));
    if (!ai.isAvailable()) {
        throw new IllegalStateException("AI System is not available");
    }
    long aiCallsCount = callsRepository.countByAISystemAndStatus(ai, CallStatus.IN_PROGRESS);
    if (aiCallsCount >= 2) {
        throw new IllegalStateException("AI System can only handle two calls at a time");
    }
    call.setAISystem(ai);
    call.setStatus(CallStatus.IN_PROGRESS);
    callsRepository.save(call);
}
```
**Contraintes appliquées :**
- L’IA doit être disponible.
- Une IA ne peut gérer que deux appels à la fois.

---

## Q3 : Déterminer si un appel nécessite un agent humain

**Méthode :**
```java
public boolean callRequiresHumanAgent(Calls call) {
    return call.getRequiredSkills().contains(CallSkills.TECHNICAL_SUPPORT);
}
```
**Règle :**
- Si l'appel nécessite la compétence `TECHNICAL_SUPPORT`, il sera pris en charge par un agent.
- Sinon, il sera géré par un système d'IA.

**Test :**
- Persister 4 appels avec mise à jour de la date/heure système et statut `ON_HOLD`.

```java
Calls call = new Calls();
call.setDate(LocalDateTime.now());
call.setStatus(CallStatus.ON_HOLD);
callsRepository.save(call);
```

---

## Q4 : Affectation automatique d’un agent compétent à un appel

**Méthode :**
```java
public void autoAssignCallsToAgents(Set<Long> callIds) {
    for (Long callId : callIds) {
        Calls call = callsRepository.findById(callId)
            .orElseThrow(() -> new EntityNotFoundException("Call not found"));
        if (callRequiresHumanAgent(call)) {
            List<Agent> agents = agentRepository.findAvailableAgentsBySkills(call.getRequiredSkills());
            if (!agents.isEmpty()) {
                Agent agent = agents.get(0);
                assignCallToAgent(call.getId(), agent.getId());
            }
        }
    }
}
```
**Algorithme :**
- Pour chaque appel, vérifier s’il nécessite un agent.
- Trouver un agent disponible et compétent.
- Affecter l’agent et mettre à jour le statut à `IN_PROGRESS`.

---

## Q5 : Affecter tous les appels donnés aux agents selon les règles

**Méthode :**
```java
public void assignCallsToAgents(Set<Long> callIds) {
    for (Long callId : callIds) {
        Calls call = callsRepository.findById(callId)
            .orElseThrow(() -> new EntityNotFoundException("Call not found"));
        if (callRequiresHumanAgent(call)) {
            List<Agent> agents = agentRepository.findAvailableAgentsBySkills(call.getRequiredSkills());
            if (!agents.isEmpty()) {
                Agent agent = agents.get(0);
                assignCallToAgent(call.getId(), agent.getId());
            }
        }
    }
}
```
**Règles :**
- Si l’appel requiert une intervention humaine, il sera affecté à un agent disponible et compétent (disponibilité = false, statut = `IN_PROGRESS`).
- Sinon, il ne sera pas affecté à un agent.

---

**Remarques :**
- Les méthodes utilitaires comme `findAvailableAgentsBySkills` doivent être définies dans le repository.
- Les entités doivent comporter les champs nécessaires (`available`, `requiredSkills`, etc.).
- Tester les services via Postman ou Swagger.

