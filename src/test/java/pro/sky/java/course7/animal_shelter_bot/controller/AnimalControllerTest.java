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
import pro.sky.java.course7.animal_shelter_bot.model.Animal;
import pro.sky.java.course7.animal_shelter_bot.model.AnimalType;
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
class AnimalControllerTest {

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
    private AnimalController animalController;

    @Test
    void getAnimalAllTest() throws Exception {

        Long firstId = 1L;
        Long secondId = 2L;
        Long thirdId = 3L;

        Animal firstAnimal = new Animal();
        firstAnimal.setId(firstId);

        Animal secondAnimal = new Animal();
        secondAnimal.setId(secondId);

        Animal thirdAnimal = new Animal();
        thirdAnimal.setId(thirdId);


        when(animalRepository.findAll())
                .thenReturn(List.of(firstAnimal, secondAnimal, thirdAnimal));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/animals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(firstId))
                .andExpect(jsonPath("$[1].id").value(secondId))
                .andExpect(jsonPath("$[2].id").value(thirdId));
    }

    @Test
    void createAnimalTest() throws Exception {

        Long id = 1L;
        AnimalType animalType = AnimalType.CAT;
        String nickname = "Begemot";
        Boolean availabilityAnimal = true;

        Animal animal = new Animal();
        animal.setId(id);
        animal.setAnimalType(animalType);
        animal.setNickname(nickname);
        animal.setAvailabilityAnimal(availabilityAnimal);

        JSONObject requestObject = new JSONObject();
        requestObject.put("id", id);
        requestObject.put("animalType", animalType.name());
        requestObject.put("nickname", nickname);
        requestObject.put("availabilityAnimal", availabilityAnimal);

        when(animalRepository.save(eq(animal)))
                .thenReturn(animal);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/animals")
                        .content(requestObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.animalType").value(animalType.name()))
                .andExpect(jsonPath("$.nickname").value(nickname))
                .andExpect(jsonPath("$.availabilityAnimal").value(availabilityAnimal));
    }

    @Test
    void deleteAnimalIdTest() throws Exception {

        Long correctId = 123L;
        Long wrongId = 321L;
        Animal animal = new Animal();
        animal.setId(correctId);

        when(animalRepository.findById(eq(correctId))).thenReturn(Optional.of(animal));
        when(animalRepository.findById(eq(wrongId))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/animals/{id}", correctId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(correctId));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/animals/{id}", wrongId))
                .andExpect(status().isNotFound());
    }
}