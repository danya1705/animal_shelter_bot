package pro.sky.java.course7.animal_shelter_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pro.sky.java.course7.animal_shelter_bot.model.Report;
import pro.sky.java.course7.animal_shelter_bot.service.ReportService;

import java.util.Collection;

@RestController
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
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
    @GetMapping("/report/{volunteer-id}")
    public Collection<Report> getReports(
            @PathVariable(name = "volunteer-id") @Parameter(description = "Volunteer ID") Long volunteerId,
            @RequestParam(name = "dateFrom") @Parameter(description = "Start of period") String dateFrom,
            @RequestParam(name = "dateTo") @Parameter(description = "End of period") String dateTo
    ) {
        return reportService.getReports(volunteerId, dateFrom, dateTo);
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
}
