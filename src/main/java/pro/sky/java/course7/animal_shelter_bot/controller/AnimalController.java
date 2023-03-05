package pro.sky.java.course7.animal_shelter_bot.controller;

import org.springframework.web.bind.annotation.*;
import pro.sky.java.course7.animal_shelter_bot.model.Animal;
import pro.sky.java.course7.animal_shelter_bot.service.AnimalService;

import java.util.List;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @GetMapping("/all")
    public List<Animal> getAnimalAll() {
        return animalService.getAnimalAll();
    }

    @PostMapping("/add")
    public Animal createAnimal(@RequestBody Animal animal) {
        return animalService.createAnimal(animal);
    }

    @DeleteMapping("delete/{id}")
    public void deleteAnimalId(@PathVariable Long id) {
        animalService.deleteAnimalId(id);
    }

    @PutMapping("/update")
    public Animal updateAnimal(@RequestBody Animal animal) {
        return animalService.updateAnimal(animal);
    }
}
