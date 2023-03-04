package pro.sky.java.course7.animal_shelter_bot.controller;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.java.course7.animal_shelter_bot.model.TrialPeriod;
import pro.sky.java.course7.animal_shelter_bot.model.UserCustodian;
import pro.sky.java.course7.animal_shelter_bot.service.CustodianService;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class CustodianController {
    private final CustodianService custodianService;

    public CustodianController(CustodianService custodianService) {
        this.custodianService = custodianService;
    }

    @PostMapping("/newCustodian")
    public ResponseEntity<UserCustodian> createCustodian(@Parameter(description = "Period to be created")
                                                         @RequestBody UserCustodian custodian) {
        return ok(custodianService.createCustodian(custodian));
    }

    @PutMapping("/update")
    public ResponseEntity<UserCustodian> updateCustodian(@Parameter(description = "Custodian to be created")
                                                         @RequestBody UserCustodian custodian) {
        UserCustodian findCustodian = custodianService.editCustodian(custodian);
        if (findCustodian == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(findCustodian);
    }

    @GetMapping("/getAllCustodian")
    public ResponseEntity<List<UserCustodian>> getCustodian() {
        List<UserCustodian> foundCustodian = custodianService.findAll();
        if (foundCustodian == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(foundCustodian);
    }


}
