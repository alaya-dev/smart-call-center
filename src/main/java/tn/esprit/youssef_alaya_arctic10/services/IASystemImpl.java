package tn.esprit.youssef_alaya_arctic10.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.youssef_alaya_arctic10.entities.AISystem;
import tn.esprit.youssef_alaya_arctic10.repositories.IAISystemRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class IASystemImpl implements  IIASystemService{
    private final IAISystemRepository systemRepository;

    @Override
    public AISystem addAISystem(AISystem system) {
        return systemRepository.save(system);
    }

    @Override
    public AISystem updateAISystem(AISystem system) {
        return systemRepository.save(system);
    }

    @Override
    public void deleteById(long systemId) {
        systemRepository.deleteById(systemId);
    }

    @Override
    public void deleteAISystem(AISystem system) {
        systemRepository.delete(system);
    }

    @Override
    public AISystem getById(long systemId) {
        return systemRepository.findById(systemId).orElseThrow(()-> new EntityNotFoundException("Calls with id " + systemId + " not found"));
    }

    @Override
    public List<AISystem> getAll() {
        return systemRepository.findAll();
    }
}
