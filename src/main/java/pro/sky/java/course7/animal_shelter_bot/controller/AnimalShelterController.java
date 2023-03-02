package pro.sky.java.course7.animal_shelter_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.java.course7.animal_shelter_bot.entity.Report;
import pro.sky.java.course7.animal_shelter_bot.entity.TrialPeriod;
import pro.sky.java.course7.animal_shelter_bot.service.ReportService;
import pro.sky.java.course7.animal_shelter_bot.service.TrialPeriodService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class AnimalShelterController {
    private final TrialPeriodService trialPeriodService;
    private final ReportService reportService;

    @GetMapping
    public String test() {
        return "It's ALIVE in AnimalShelterController!!!!";
    }

    public AnimalShelterController(TrialPeriodService trialPeriodService, ReportService reportService) {
        this.trialPeriodService = trialPeriodService;
        this.reportService = reportService;
    }

    @Operation(summary = "Get list of all Trial periods", tags = "Trial period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "There's the list",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrialPeriod.class))})
    })
    @GetMapping("/getAll")
    public ResponseEntity<List<TrialPeriod>> getTrialPeriodList() {
        List<TrialPeriod> foundStudent = trialPeriodService.findAll();
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @Operation(summary = "Create new Trial period", tags = "Trial period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trial period is created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrialPeriod.class))})
    })
    @PostMapping("/new")
    public ResponseEntity<TrialPeriod> createTrialPeriod(@Parameter(description = "Period to be created")
                                                         @RequestBody TrialPeriod period) {
        return ok(trialPeriodService.createPeriod(period));
    }

    @Operation(summary = "Change an existed Trial period", tags = "Trial period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trial period has been changed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrialPeriod.class))})
    })
    @PutMapping("/new")
    public ResponseEntity<TrialPeriod> updateTrialPeriod(@Parameter(description = "Period to be created")
                                                         @RequestBody TrialPeriod period) {
        TrialPeriod findTrialPeriod = trialPeriodService.editTrialPeriod(period);
        if (findTrialPeriod == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(
            summary = "Get custodians reports for a selected period",
            tags = "Reports",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found reports",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Report.class))
                            )
                    )
            }
    )
    @GetMapping("/report/{volunteer}")
    public Collection<Report> getReports(
            @PathVariable(name = "volunteer") @Parameter(description = "Volunteer") String volunteer,
            @RequestParam(name = "dateFrom") @Parameter(description = "Start of period") LocalDate dateFrom,
            @RequestParam(name = "dateTo") @Parameter(description = "End of period") LocalDate dateTo
    ) {
        return reportService.getReports(volunteer, dateFrom, dateTo);
    }

    @Operation(
            summary = "Send message to custodian",
            tags = "Reports",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Message sent "
                    )
            }
    )
    @PutMapping("/message/{user-id}")
    public void sendMessage(
            @PathVariable(name = "user-id") @Parameter(description = "User ID") Long id,
            @RequestBody @Parameter(description = "Enter message text") String text
    ) {
        reportService.sendMessage(id, text);
    }

    @Operation(summary = "Closing the trial period", tags = "Trial period")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Trial period is closed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrialPeriod.class))})
    })
    @DeleteMapping("{id}")
    public ResponseEntity<TrialPeriod> closureTrialPeriod(@PathVariable Long id) {
        trialPeriodService.deletePeriod(id);
        return ResponseEntity.ok().build();
    }
}
