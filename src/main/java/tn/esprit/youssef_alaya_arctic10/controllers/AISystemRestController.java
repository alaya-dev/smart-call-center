package tn.esprit.youssef_alaya_arctic10.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.youssef_alaya_arctic10.entities.AISystem;
import tn.esprit.youssef_alaya_arctic10.services.IIASystemService;

@RequestMapping("ai-system")
@RestController
@RequiredArgsConstructor
public class AISystemRestController {
    private final IIASystemService aiSystemService;

    @PostMapping("add")
    public void addAISystem(@RequestBody AISystem aiSystem) {
        aiSystemService.addAISystem(aiSystem);
    }

    @PutMapping("update")
    public void updateAISystem(@RequestBody AISystem aiSystem) {
        aiSystemService.updateAISystem(aiSystem);
    }

    @DeleteMapping("delete/{id}")
    public void deleteAISystemById(@PathVariable long id) {
        aiSystemService.deleteById(id);
    }

    @GetMapping("get/{id}")
    public AISystem getAISystemById(@PathVariable long id) {
        return aiSystemService.getById(id);
    }

    @GetMapping("get")
    public void getAll() {
        aiSystemService.getAll();
    }
}
