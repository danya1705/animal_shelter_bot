package pro.sky.java.course7.animal_shelter_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.java.course7.animal_shelter_bot.model.Photo;
import pro.sky.java.course7.animal_shelter_bot.model.Report;
import pro.sky.java.course7.animal_shelter_bot.service.ReportService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/reports")
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
    @GetMapping("/{volunteer-id}")
    public Collection<Report> getReports(
            @PathVariable(name = "volunteer-id") @Parameter(description = "Volunteer ID") Long volunteerId,
            @RequestParam(name = "dateFrom") @Parameter(description = "Start of period") String dateFrom,
            @RequestParam(name = "dateTo") @Parameter(description = "End of period") String dateTo) {
        return reportService.getReports(volunteerId, dateFrom, dateTo);
    }

    @Operation(
            summary = "Send message to custodian",
            tags = "Reports",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Message sent"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Telegram ID is not found for this user"
                    )
            }
    )
    @PutMapping("/{user-id}")
    public ResponseEntity sendMessage(
            @PathVariable(name = "user-id") @Parameter(description = "User ID") Long id,
            @RequestBody String text) {
        if (reportService.sendMessage(id, text)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Get photo from report by report ID",
            tags = "Reports",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Found photo",
                            content = @Content(mediaType = MediaType.IMAGE_JPEG_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Report with such ID was not found in database",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Probably photo was deleted from Telegram history",
                            content = @Content
                    )
            }
    )
    @GetMapping(value = "/photo/{id}")
    public ResponseEntity<byte[]> getPhoto(
            @PathVariable(name = "id") @Parameter(description = "Report ID") Long id) {
        Optional<Report> report = reportService.getReportById(id);
        if (report.isPresent()) {
            Photo photo = reportService.getPhotoById(report.get().getPhoto());
            if (photo != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType(photo.getMediaType()));
                headers.setContentLength(photo.getFileSize());
                return ResponseEntity.status(HttpStatus.OK).headers(headers).body(photo.getData());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
