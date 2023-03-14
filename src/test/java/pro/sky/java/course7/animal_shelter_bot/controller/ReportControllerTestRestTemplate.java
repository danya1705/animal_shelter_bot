package pro.sky.java.course7.animal_shelter_bot.controller;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import pro.sky.java.course7.animal_shelter_bot.model.Report;
import pro.sky.java.course7.animal_shelter_bot.model.UserCustodian;
import pro.sky.java.course7.animal_shelter_bot.service.*;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class ReportControllerTestRestTemplate {

    @LocalServerPort
    private int port;
    @Autowired
    private ReportController reportController;
    @Autowired
    private ReportService reportService;
    @Autowired
    private CustodianService custodianService;
    @Autowired
    private TestRestTemplate testRestTemplate;

    private Long userId;
    private boolean deleteUserAfterTest;

    @Test
    void contextLoads() {
        Assertions.assertThat(reportController).isNotNull();
    }

    @Test
    void getPhotoTest() {

        getTestUserId();
        Report report = createTestReport();
        Long reportId = report.getId();

        ResponseEntity<byte[]> responseEntity = testRestTemplate
                .getForEntity("http://localhost:" + port + "/reports/photo/" + reportId, byte[].class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getHeaders().getContentType()).isEqualTo(MediaType.IMAGE_JPEG);
        assertThat(responseEntity.getHeaders().getContentLength()).isEqualTo(20184);

        reportService.deleteReport(reportId);
        if (deleteUserAfterTest) custodianService.deleteUserCustodian(userId);

        responseEntity = testRestTemplate
                .getForEntity("http://localhost:" + port + "/reports/photo/" + reportId, byte[].class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

    }

    private void getTestUserId() {
        Optional<UserCustodian> userCustodianOptional = custodianService.findExistingUser();
        if (userCustodianOptional.isPresent()) {
            userId = userCustodianOptional.get().getId();
            deleteUserAfterTest = false;
        } else {
            UserCustodian userCustodian = new UserCustodian();
            custodianService.createCustodian(userCustodian);
            userId = custodianService.findExistingUser().get().getId();
            deleteUserAfterTest = true;
        }
    }

    private Report createTestReport() {
        Report report = new Report();
        report.setUserId(userId);
        report.setPhoto("AgACAgIAAxkBAANyZAyXT2VvvuCKW-r_Fg2lv2Rz7KgAAkvHMRsMc2hIDd31jeSuWZoBAAMCAAN4AAMvBA");
        report.setReportDate(LocalDate.of(2023, 1, 1));
        report.setDiet("test");
        report.setBehaviour("test");
        report.setWellbeing("test");
        return reportService.createReport(report);
    }

}