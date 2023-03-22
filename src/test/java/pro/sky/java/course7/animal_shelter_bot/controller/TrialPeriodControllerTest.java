package pro.sky.java.course7.animal_shelter_bot.controller;

import com.pengrad.telegrambot.TelegramBot;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.java.course7.animal_shelter_bot.model.TrialPeriod;
import pro.sky.java.course7.animal_shelter_bot.repository.*;
import pro.sky.java.course7.animal_shelter_bot.service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class TrialPeriodControllerTest {

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
    private TelegramBot telegramBot;
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
    private TrialPeriodController trialPeriodController;

    @Test
    void getTrialPeriodListTest() throws Exception {

        Long firstId = 1L;
        Long secondId = 2L;
        Long thirdId = 3L;

        TrialPeriod firstTrialPeriod = new TrialPeriod();
        firstTrialPeriod.setId(firstId);

        TrialPeriod secondTrialPeriod = new TrialPeriod();
        secondTrialPeriod.setId(secondId);

        TrialPeriod thirdTrialPeriod = new TrialPeriod();
        thirdTrialPeriod.setId(thirdId);

        when(trialPeriodRepository.findAll())
                .thenReturn(List.of(firstTrialPeriod, secondTrialPeriod, thirdTrialPeriod));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/trial-periods"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(firstId))
                .andExpect(jsonPath("$[1].id").value(secondId))
                .andExpect(jsonPath("$[2].id").value(thirdId));
    }

    @Test
    void createTrialPeriodTest() throws Exception {

        Long id = 1L;
        Long volunteerId = 123L;
        Long userId = 456L;
        Long animalId = 789L;

        TrialPeriod trialPeriod = new TrialPeriod();
        trialPeriod.setId(id);
        trialPeriod.setVolunteerId(volunteerId);
        trialPeriod.setUserId(userId);
        trialPeriod.setAnimalId(animalId);

        JSONObject requestObject = new JSONObject();
        requestObject.put("id", id);
        requestObject.put("volunteerId", volunteerId);
        requestObject.put("userId", userId);
        requestObject.put("animalId", animalId);

        when(trialPeriodRepository.save(eq(trialPeriod)))
                .thenReturn(trialPeriod);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/trial-periods")
                        .content(requestObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.volunteerId").value(volunteerId))
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.animalId").value(animalId));
    }

    @Test
    void updateTrialPeriodTest() throws Exception {

        /*
         * Создаём вводные данные:
         * id, по которому будет найден TrialPeriod, wrongId, по которому не будет найден период,
         * старое и новое имена волонтёров, а также формируем из них старый и новый TrialPeriod.
         */
        Long id = 123L;
        Long wrongId = 321L;
        Long oldVolunteerId = 1L;
        Long newVolunteerId = 2L;

        TrialPeriod oldTrialPeriod = new TrialPeriod();
        oldTrialPeriod.setId(id);
        oldTrialPeriod.setVolunteerId(oldVolunteerId);

        TrialPeriod newTrialPeriod = new TrialPeriod();
        newTrialPeriod.setId(id);
        newTrialPeriod.setVolunteerId(newVolunteerId);

        /*
         * Создаем тело корректного запроса.
         */
        JSONObject requestObject = new JSONObject();
        requestObject.put("id", id);
        requestObject.put("volunteer_id", newVolunteerId);

        /*
         * Настраиваем выдачу ответа из мока TrialPeriodRepository
         */
        when(trialPeriodRepository.findById(eq(123L))).thenReturn(Optional.of(oldTrialPeriod));
        when(trialPeriodRepository.findById(eq(321L))).thenReturn(Optional.empty());
        when(trialPeriodRepository.save(any(TrialPeriod.class))).thenReturn(newTrialPeriod);

        /*
         * Тест должен переименовать волонтёра. Проверяем статус 200 и значения полей id и volunteer
         * в теле Response.
         */
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/trial-periods")
                        .content(requestObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.volunteerId").value(newVolunteerId));

        /*
         * Создаем тело некорректного запроса (с id, на который мок выдаст пустой Optional).
         */
        requestObject = new JSONObject();
        requestObject.put("id", wrongId);

        /*
         * Тест должен вернуть статус 404.
         */
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/trial-periods")
                        .content(requestObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void closureTrialPeriodTest() throws Exception {

        Long id = 123L;
        Long wrongId = 321L;

        TrialPeriod trialPeriod = new TrialPeriod();
        trialPeriod.setId(id);

        when(trialPeriodRepository.findById(eq(id))).thenReturn(Optional.of(trialPeriod));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/trial-periods/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        when(trialPeriodRepository.findById(eq(wrongId))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/trial-periods/{id}", wrongId))
                .andExpect(status().isNotFound());
    }

    @Test
    void extendTrialPeriodFor15DaysTest() throws Exception {

        Long id = 123L;
        Long wrongId = 321L;
        LocalDate oldEndDate = LocalDate.of(2023, 1, 25);
        LocalDate newEndDate = LocalDate.of(2023, 2, 9);

        TrialPeriod oldTrialPeriod = new TrialPeriod();
        oldTrialPeriod.setId(id);
        oldTrialPeriod.setEndDate(oldEndDate);

        TrialPeriod newTrialPeriod = new TrialPeriod();
        newTrialPeriod.setId(id);
        newTrialPeriod.setEndDate(newEndDate);
        newTrialPeriod.setStartDate(oldEndDate);
        newTrialPeriod.setVolunteerId(id);
        newTrialPeriod.setUserId(id);
        newTrialPeriod.setAnimalId(id);

        when(trialPeriodRepository.findById(eq(id))).thenReturn(Optional.of(oldTrialPeriod));
        when(trialPeriodRepository.save(eq(newTrialPeriod))).thenReturn(newTrialPeriod);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/trial-periods/extend15/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.endDate").value(newEndDate.toString()));

        when(trialPeriodRepository.findById(eq(wrongId))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/trial-periods/extend15/{id}", wrongId))
                .andExpect(status().isNotFound());
    }

    @Test
    void extendTrialPeriodFor30DaysTest() throws Exception {

        Long id = 123L;
        Long wrongId = 321L;
        LocalDate oldEndDate = LocalDate.of(2023, 1, 25);
        LocalDate newEndDate = LocalDate.of(2023, 2, 24);

        TrialPeriod oldTrialPeriod = new TrialPeriod();
        oldTrialPeriod.setId(id);
        oldTrialPeriod.setEndDate(oldEndDate);

        TrialPeriod newTrialPeriod = new TrialPeriod();
        newTrialPeriod.setId(id);
        newTrialPeriod.setEndDate(newEndDate);
        newTrialPeriod.setStartDate(oldEndDate);
        newTrialPeriod.setVolunteerId(id);
        newTrialPeriod.setUserId(id);
        newTrialPeriod.setAnimalId(id);

        when(trialPeriodRepository.findById(eq(id))).thenReturn(Optional.of(oldTrialPeriod));
        when(trialPeriodRepository.save(eq(newTrialPeriod))).thenReturn(newTrialPeriod);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/trial-periods/extend30/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.endDate").value(newEndDate.toString()));

        when(trialPeriodRepository.findById(eq(wrongId))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/trial-periods/extend30/{id}", wrongId))
                .andExpect(status().isNotFound());
    }
}