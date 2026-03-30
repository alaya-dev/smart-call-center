package tn.esprit.youssef_alaya_arctic10.services;

import tn.esprit.youssef_alaya_arctic10.entities.AISystem;
import tn.esprit.youssef_alaya_arctic10.entities.Calls;

import java.util.List;

public interface IIASystemService {
    AISystem addAISystem(AISystem system);
    AISystem updateAISystem(AISystem system);
    void deleteById(long systemId);
    void deleteAISystem(AISystem system);
    AISystem getById(long systemId);
    List<AISystem> getAll();
}
