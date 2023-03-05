package pro.sky.java.course7.animal_shelter_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.java.course7.animal_shelter_bot.model.Animal;
import pro.sky.java.course7.animal_shelter_bot.model.UserCustodian;
import pro.sky.java.course7.animal_shelter_bot.service.AnimalService;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/animal")
public class AnimalController {

    private final AnimalService animalService;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @Operation(summary = "Get the whole list of Animals", tags = "Animal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Animals",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Animal.class))})
    })
    @GetMapping("/all")
    public ResponseEntity<List<Animal>> getAnimalAll() {
        return ResponseEntity.ok(animalService.getAnimalAll());
    }

    @Operation(summary = "Create new Animal", tags = "Animal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New Animal is created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Animal.class))})
    })
    @PostMapping("/add")
    public ResponseEntity<Animal> createAnimal(@Parameter(description = "The animal that will be created")
                                               @RequestBody Animal animal) {
        return ok(animalService.createAnimal(animal));
    }

    @Operation(summary = "Deleting an Animal by id", tags = "Animal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Animal is deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Animal.class))})
    })
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Animal> deleteAnimalId(@PathVariable Long id) {
        if (animalService.deleteAnimalId(id) == null) {
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(animalService.deleteAnimalId(id));
    }

    @Operation(summary = "Updating Animal data", tags = "Animal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Animal is updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Animal.class))})
    })
    @PutMapping("/update")
    public Animal updateAnimal(@Parameter(description = "Information about the animal has been updated")
                               @RequestBody Animal animal) {
        return animalService.updateAnimal(animal);
    }
}
