package pro.sky.java.course7.animal_shelter_bot.service;

import org.springframework.stereotype.Service;
import pro.sky.java.course7.animal_shelter_bot.exception.AnimalNotFoundException;
import pro.sky.java.course7.animal_shelter_bot.model.Animal;
import pro.sky.java.course7.animal_shelter_bot.repository.AnimalRepository;

import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository animalRepository;

    public AnimalService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public List<Animal> getAnimalAll() {
        return animalRepository.findAll();
    }

    public Animal getAnimalById(long id) {
       return animalRepository.findById(id).orElse(null);
    }

    public Animal createAnimal(Animal animal) {
        return animalRepository.save(animal);
    }

    public Animal deleteAnimalId(Long id) {
        Animal animalDelete = animalRepository.findById(id).orElse(null);
        if (animalDelete != null) {
            animalRepository.deleteById(id);
        }
        return null;
    }

    public Animal updateAnimal(Animal animal) {
        Animal findAnimal = getAnimalById(animal.getId());
        if (findAnimal == null) {
            throw new AnimalNotFoundException("Animal not found.");
        }
        return animalRepository.save(animal);
    }
}
