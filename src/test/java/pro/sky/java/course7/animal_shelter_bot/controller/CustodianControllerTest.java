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
import pro.sky.java.course7.animal_shelter_bot.model.UserCustodian;
import pro.sky.java.course7.animal_shelter_bot.repository.*;
import pro.sky.java.course7.animal_shelter_bot.service.*;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class CustodianControllerTest {

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
    private CustodianController custodianController;

    @Test
    void createCustodianTest() throws Exception {

        Long id = 123L;
        Long userChatId = 1234L;
        String fullName = "Ivan Ivanov";
        String contacts = "Blah blah blah";

        UserCustodian userCustodian = new UserCustodian();
        userCustodian.setId(id);
        userCustodian.setUserChatId(userChatId);
        userCustodian.setFullName(fullName);
        userCustodian.setContacts(contacts);

        JSONObject requestObject = new JSONObject();
        requestObject.put("id", id);
        requestObject.put("userChatId", userChatId);
        requestObject.put("fullName", fullName);
        requestObject.put("contacts", contacts);

        when(userCustodianRepository.save(eq(userCustodian)))
                .thenReturn(userCustodian);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/custodians")
                        .content(requestObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.userChatId").value(userChatId))
                .andExpect(jsonPath("$.fullName").value(fullName))
                .andExpect(jsonPath("$.contacts").value(contacts));
    }

    @Test
    void updateCustodianTest() throws Exception {

        Long id = 123L;
        Long wrongId = 231L;
        String oldName = "Ivan Ivanov";
        String newName = "Petr Petrov";

        UserCustodian oldCustodian = new UserCustodian();
        oldCustodian.setId(id);
        oldCustodian.setFullName(oldName);

        UserCustodian newCustodian = new UserCustodian();
        newCustodian.setId(id);
        newCustodian.setFullName(newName);

        JSONObject requestObject = new JSONObject();
        requestObject.put("id", id);
        requestObject.put("fullName", newName);

        when(userCustodianRepository.findById(eq(id))).thenReturn(Optional.of(oldCustodian));
        when(userCustodianRepository.findById(eq(wrongId))).thenReturn(Optional.empty());
        when(userCustodianRepository.save(eq(newCustodian))).thenReturn(newCustodian);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/custodians")
                        .content(requestObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.fullName").value(newName));

        requestObject.put("id", wrongId);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/custodians")
                        .content(requestObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCustodianTest() throws Exception {

        Long firstId = 1L;
        Long secondId = 2L;
        Long thirdId = 3L;

        UserCustodian firstCustodian = new UserCustodian();
        firstCustodian.setId(firstId);

        UserCustodian secondCustodian = new UserCustodian();
        secondCustodian.setId(secondId);

        UserCustodian thirdCustodian = new UserCustodian();
        thirdCustodian.setId(thirdId);

        when(userCustodianRepository.findAll())
                .thenReturn(List.of(firstCustodian, secondCustodian, thirdCustodian));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/custodians"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id").value(firstId))
                .andExpect(jsonPath("$[1].id").value(secondId))
                .andExpect(jsonPath("$[2].id").value(thirdId));
    }

    @Test
    void isCustodianByChatIdExistsTest() throws Exception {

        Long id = 1L;
        Long chatID = 123L;
        Long wrongChatId = 321L;
        UserCustodian custodian = new UserCustodian();
        custodian.setId(id);
        custodian.setUserChatId(chatID);

        when(userCustodianRepository.findUserCustodianByUserChatId(eq(chatID))).thenReturn(custodian);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/custodians/exists-by-chat-id/{chat-id}", chatID))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));

        when(userCustodianRepository.findUserCustodianByUserChatId(eq(wrongChatId))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/custodians/exists-by-chat-id/{chat-id}", wrongChatId))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}