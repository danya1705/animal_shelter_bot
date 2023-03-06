package pro.sky.java.course7.animal_shelter_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.java.course7.animal_shelter_bot.model.Volunteer;
import pro.sky.java.course7.animal_shelter_bot.model.UserCustodian;
import pro.sky.java.course7.animal_shelter_bot.service.VolunteerService;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController {

    private final VolunteerService volunteerService;

    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @Operation(summary = "Get the whole list of Volunteers", tags = "Volunteer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Volunteers",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Volunteer.class))})
    })
    @GetMapping("/all")
    public ResponseEntity<List<Volunteer>> getVolunteerAll() {
        return ResponseEntity.ok(volunteerService.getVolunteerAll());
    }

    @Operation(summary = "Create new Volunteer", tags = "Volunteer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New Volunteer is created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Volunteer.class))})
    })
    @PostMapping("/add")
    public ResponseEntity<Volunteer> createVolunteer(@Parameter(description = "The volunteer that will be created")
                                               @RequestBody Volunteer volunteer) {
        return ok(volunteerService.createVolunteer(volunteer));
    }

    @Operation(summary = "Deleting an Volunteer by id", tags = "Volunteer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Volunteer is deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Volunteer.class))})
    })
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Volunteer> deleteVolunteerId(@PathVariable Long id) {
        if (volunteerService.deleteVolunteerId(id) == null) {
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(volunteerService.deleteVolunteerId(id));
    }

    @Operation(summary = "Updating Volunteer data", tags = "Volunteer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Volunteer is updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Volunteer.class))})
    })
    @PutMapping("/update")
    public Volunteer updateVolunteer(@Parameter(description = "Information about the Volunteer has been updated")
                               @RequestBody Volunteer volunteer) {
        return volunteerService.updateVolunteer(volunteer);
    }
}

}
