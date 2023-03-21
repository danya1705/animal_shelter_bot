package pro.sky.java.course7.animal_shelter_bot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.java.course7.animal_shelter_bot.model.UserCustodian;
import pro.sky.java.course7.animal_shelter_bot.service.CustodianService;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/custodians")
public class CustodianController {
    private final CustodianService custodianService;

    public CustodianController(CustodianService custodianService) {
        this.custodianService = custodianService;
    }


    @Operation(summary = "Create new User Custodian", tags = "Custodian")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New Custodian is created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserCustodian.class))})
    })
    @PostMapping("")
    public ResponseEntity<UserCustodian> createCustodian(@Parameter(description = "Period to be created")
                                                         @RequestBody UserCustodian custodian) {
        return ok(custodianService.createCustodian(custodian));
    }
    @Operation(summary = "Change an existed User Custodian", tags = "Custodian")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Custodian has been changed",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserCustodian.class))})
    })
    @PutMapping("")
    public ResponseEntity<UserCustodian> updateCustodian(@Parameter(description = "Custodian to be updated")
                                                         @RequestBody UserCustodian custodian) {
        UserCustodian findCustodian = custodianService.editCustodian(custodian);
        if (findCustodian == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findCustodian);
    }
    @Operation(summary = "Get list of all User Custodian", tags = "Custodian")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "There's the list",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserCustodian.class))})
    })
    @GetMapping("")
    public ResponseEntity<List<UserCustodian>> getCustodian() {
        List<UserCustodian> foundCustodian = custodianService.findAll();
        if (foundCustodian == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundCustodian);
    }

    @Operation(summary = "Get true/false about custodian existing by it's chatId", tags = "Custodian")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Answer",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserCustodian.class))})
    })
    @GetMapping("/exists-by-chat-id/{chat-id}")
    public Boolean isCustodianByChatIdExists(@PathVariable("chat-id") Long chatId) {
        return custodianService.findUserByChatId(chatId);
    }
}
