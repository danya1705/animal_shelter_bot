package pro.sky.java.course7.animal_shelter_bot.service;

import org.springframework.stereotype.Service;
import pro.sky.java.course7.animal_shelter_bot.exception.AnimalNotFoundException;
import pro.sky.java.course7.animal_shelter_bot.model.Animal;
import pro.sky.java.course7.animal_shelter_bot.repository.AnimalRepository;

import java.util.List;
import java.util.Optional;

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

    public Optional<Animal> deleteAnimalId(Long id) {
        Optional<Animal> deletedAnimal = animalRepository.findById(id);
        deletedAnimal.ifPresent(animal -> animalRepository.deleteById(id));
        return deletedAnimal;
    }

    public Animal updateAnimal(Animal animal) {
        Animal findAnimal = getAnimalById(animal.getId());
        if (findAnimal == null) {
            throw new AnimalNotFoundException("Animal not found.");
        }
        return animalRepository.save(animal);
    }
}
