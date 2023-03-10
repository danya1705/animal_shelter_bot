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
import pro.sky.java.course7.animal_shelter_bot.model.Volunteer;
import pro.sky.java.course7.animal_shelter_bot.repository.*;
import pro.sky.java.course7.animal_shelter_bot.service.*;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class VolunteerControllerTest {

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
    private VolunteerController volunteerController;

    @Test
    void getVolunteerAllTest() throws Exception {

        Long firstId = 1L;
        Long secondId = 2L;
        Long thirdId = 3L;

        Volunteer firstVolunteer = new Volunteer();
        firstVolunteer.setId(firstId);

        Volunteer secondVolunteer = new Volunteer();
        secondVolunteer.setId(secondId);

        Volunteer thirdVolunteer = new Volunteer();
        thirdVolunteer.setId(thirdId);

        when(volunteerRepositiory.findAll())
                .thenReturn(List.of(firstVolunteer, secondVolunteer, thirdVolunteer));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/volunteer/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(firstId))
                .andExpect(jsonPath("$[1].id").value(secondId))
                .andExpect(jsonPath("$[2].id").value(thirdId));
    }

    @Test
    void createVolunteerTest() throws Exception {

        Long id = 123L;
        String volunteerName = "Ace Ventura";
        Long chatId = 1234L;
        boolean available = true;

        Volunteer volunteer = new Volunteer();
        volunteer.setId(id);
        volunteer.setVolunteerName(volunteerName);
        volunteer.setChatId(chatId);
        volunteer.setAvailable(available);

        JSONObject requestObject = new JSONObject();
        requestObject.put("id", id);
        requestObject.put("volunteerName", volunteerName);
        requestObject.put("chatId", chatId);
        requestObject.put("available", available);

        when(volunteerRepositiory.save(eq(volunteer)))
                .thenReturn(volunteer);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/volunteer/add")
                        .content(requestObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.volunteerName").value(volunteerName))
                .andExpect(jsonPath("$.chatId").value(chatId))
                .andExpect(jsonPath("$.available").value(available));
    }

    @Test
    void deleteVolunteerIdTest() throws Exception {

        Long id = 123L;
        Long wrongId = 321L;
        Volunteer volunteer = new Volunteer();
        volunteer.setId(id);

        when(volunteerRepositiory.findById(eq(id))).thenReturn(Optional.of(volunteer));
        when(volunteerRepositiory.findById(eq(wrongId))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/volunteer/delete/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/volunteer/delete/{id}", wrongId))
                .andExpect(status().isNotFound());

    }
}