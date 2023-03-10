package pro.sky.java.course7.animal_shelter_bot.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.java.course7.animal_shelter_bot.listener.TelegramBotUpdatesListener;
import pro.sky.java.course7.animal_shelter_bot.model.Report;
import pro.sky.java.course7.animal_shelter_bot.repository.*;
import pro.sky.java.course7.animal_shelter_bot.service.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TrialPeriodRepository trialPeriodRepository;
    @MockBean
    private ReportRepository reportRepository;
    @MockBean
    private UserCustodianRepository userCustodianRepository;
    @MockBean
    private AnimalRepository animalRepository;
    @MockBean
    private VolunteerRepositiory volunteerRepositiory;
    @MockBean
    private TelegramBotUpdatesListener telegramBotUpdatesListener;
    @SpyBean
    private TrialPeriodService trialPeriodService;
    @SpyBean
    private ReportService reportService;
    @SpyBean
    private AnimalService animalService;
    @SpyBean
    private CustodianService custodianService;
    @SpyBean
    private VolunteerService volunteerService;
    @InjectMocks
    private ReportController reportController;

    @Test
    void getReportsTest() throws Exception {

        /*
         * Создаем список id попечителей для некого волонтёра
         */
        Collection<Long> idList = List.of(1L, 2L, 3L);

        /*
         * Создаем 4 тестовых отчета для мока репозитория
         */
        Report report11 = new Report();
        report11.setId(11L);
        report11.setReportDate(LocalDate.of(2023, Month.JANUARY, 11));
        report11.setUserId(1L);

        Report report12 = new Report();
        report12.setId(12L);
        report12.setReportDate(LocalDate.of(2023, Month.JANUARY, 12));
        report12.setUserId(1L);

        Report report20 = new Report();
        report20.setId(20L);
        report20.setReportDate(LocalDate.of(2023, Month.FEBRUARY, 20));
        report20.setUserId(2L);

        Report report30 = new Report();
        report30.setId(30L);
        report30.setReportDate(LocalDate.of(2023, Month.MARCH, 30));
        report30.setUserId(3L);

        /*
         * Настраиваем выдачу отчетов из моков trialPeriodRepository и reportRepository
         * в зависимости от id попечителя
         */
        when(trialPeriodRepository.findUserIdByVolunteerId(any(Long.class))).thenReturn(idList);
        when(reportRepository.findReportsByUserId(eq(1L))).thenReturn(List.of(report11, report12));
        when(reportRepository.findReportsByUserId(eq(2L))).thenReturn(List.of(report20));
        when(reportRepository.findReportsByUserId(eq(3L))).thenReturn(List.of(report30));

        /*
         * Тест должен выдать все 4 отчета и проверить их id
         */
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/report/{volunteer-id}", 1)
                        .param("dateFrom", "2023-01-01")
                        .param("dateTo", "2023-04-01"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].id").value(report11.getId()))
                .andExpect(jsonPath("$[1].id").value(report12.getId()))
                .andExpect(jsonPath("$[2].id").value(report20.getId()))
                .andExpect(jsonPath("$[3].id").value(report30.getId()));

        /*
         * Тест должен выдать только 2 из 4 отчетов и проверить их id
         */
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/report/{volunteer-id}", 1)
                        .param("dateFrom", "2023-01-12")
                        .param("dateTo", "2023-03-01"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(report12.getId()))
                .andExpect(jsonPath("$[1].id").value(report20.getId()));

        /*
         * Тест должен не найти ни одного отчета в указанный интервал дат и выдать пустой список
         */
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/report/{volunteer-id}", 1)
                        .param("dateFrom", "2023-05-01")
                        .param("dateTo", "2023-06-01"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

        /*
         * Создаем некого "волонтёра", от попечителей которого не будет отчетов
         * Обновляем выдачу из trialPeriodRepository
         */
        Collection<Long> idListNull = List.of(4L, 5L);
        when(trialPeriodRepository.findUserIdByVolunteerId(any(Long.class))).thenReturn(idListNull);

        /*
         Тест должен не найти ни одного отчета с нужными id попечителей и выдать пустой список
         */
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/report/{volunteer-id}", 1)
                        .param("dateFrom", "2023-01-01")
                        .param("dateTo", "2023-12-31"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(0)));

    }
}