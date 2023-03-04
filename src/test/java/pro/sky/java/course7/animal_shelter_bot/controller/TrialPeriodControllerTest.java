package pro.sky.java.course7.animal_shelter_bot.controller;

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
import pro.sky.java.course7.animal_shelter_bot.listener.TelegramBotUpdatesListener;
import pro.sky.java.course7.animal_shelter_bot.model.TrialPeriod;
import pro.sky.java.course7.animal_shelter_bot.repository.ReportRepository;
import pro.sky.java.course7.animal_shelter_bot.repository.TrialPeriodRepository;
import pro.sky.java.course7.animal_shelter_bot.service.ReportService;
import pro.sky.java.course7.animal_shelter_bot.service.TrialPeriodService;

import java.util.Optional;

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
    private TelegramBotUpdatesListener telegramBotUpdatesListener;
    @SpyBean
    private TrialPeriodService trialPeriodService;
    @SpyBean
    private ReportService reportService;
    @InjectMocks
    private TrialPeriodController trialPeriodController;

    @Test
    void updateTrialPeriodTest() throws Exception {

        /*
         * Создаём вводные данные:
         * id, по которому будет найден TrialPeriod, wrongId, по которому не будет найден период,
         * старое и новое имена волонтёров, а также формируем из них старый и новый TrialPeriod.
         */
        Long id = 123L;
        Long wrongId = 321L;
        String oldVolunteer = "Doctor Aibolit";
        String newVolunteer = "Ace Ventura";

        TrialPeriod oldTrialPeriod = new TrialPeriod();
        oldTrialPeriod.setId(id);
        oldTrialPeriod.setVolunteer(oldVolunteer);

        TrialPeriod newTrialPeriod = new TrialPeriod();
        newTrialPeriod.setId(id);
        newTrialPeriod.setVolunteer(newVolunteer);

        /*
         * Создаем тело корректного запроса.
         */
        JSONObject requestObject = new JSONObject();
        requestObject.put("id", id);
        requestObject.put("volunteer", newVolunteer);

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
                        .put("/new")
                        .content(requestObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.volunteer").value(newVolunteer));

        /*
         * Создаем тело некорректного запроса (с id, на который мок выдаст пустой Optional).
         */
        requestObject = new JSONObject();
        requestObject.put("id", wrongId);

        /*
         * Тест должен вернуть статус 404.
         */
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/new")
                        .content(requestObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}