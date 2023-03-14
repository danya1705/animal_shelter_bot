package pro.sky.java.course7.animal_shelter_bot.service;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.java.course7.animal_shelter_bot.model.BotStatus;
import pro.sky.java.course7.animal_shelter_bot.model.UserCustodian;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateServiceTest {

    @Mock
    private CustodianService custodianService;
    @Mock
    private KeyboardService keyboardService;
    @InjectMocks
    private UpdateService updateService;
    @Captor
    ArgumentCaptor<UserCustodian> userCaptor;

    @Test
    void shouldSaveNewUserInDatabase() throws URISyntaxException, IOException {

        String json = Files.readString(
                Paths.get(UpdateServiceTest.class.getResource("contact_update.json").toURI()));
        Update update = getUpdate(json, "/start");

        when(custodianService.findUserByChatId(any(Long.class))).thenReturn(false);

        SendMessage message = updateService.updateHandler(update);
        verify(custodianService).createCustodian(userCaptor.capture());
        assertThat(userCaptor.getValue().getUserChatId()).isEqualTo(123L);
        assertThat(userCaptor.getValue().getFullName()).isEqualTo("Ivan Ivanov");
        assertThat(userCaptor.getValue().getContacts()).isEqualTo("+79876543210");

    }

    @Test
    void handleUnregisteredUserTest() throws URISyntaxException, IOException {

        String json = Files.readString(
                Paths.get(UpdateServiceTest.class.getResource("text_update.json").toURI()));
        Update update = getUpdate(json, "/start");

        when(custodianService.findUserByChatId(any(Long.class))).thenReturn(false);

        SendMessage message = updateService.updateHandler(update);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.UNREGISTERED_USER_MESSAGE.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

    }

    @Test
    void handleGreetingsMessageTest() throws URISyntaxException, IOException {

        String json = Files.readString(
                Paths.get(UpdateServiceTest.class.getResource("callback_update.json").toURI()));
        Update update = getUpdate(json, "S1Callback");

        when(custodianService.findUserByChatId(any(Long.class))).thenReturn(true);
        updateService.editStatusMap(123L, BotStatus.GREETINGS_MESSAGE);

        SendMessage message = updateService.updateHandler(update);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.GREETINGS_MESSAGE.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

    }

    @Test
    void handleGetStartButtonCallbackTest() throws URISyntaxException, IOException {

        String json = Files.readString(
                Paths.get(UpdateServiceTest.class.getResource("callback_update.json").toURI()));
        Update update = getUpdate(json, "S1Callback");
        Update updateWrong = getUpdate(json, "WrongCallback");

        when(custodianService.findUserByChatId(any(Long.class))).thenReturn(true);
        updateService.editStatusMap(123L, BotStatus.START_BUTTON);

        SendMessage message = updateService.updateHandler(update);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.STAGE_NULL_MENU.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.START_BUTTON);
        message = updateService.updateHandler(updateWrong);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.UNHANDLED_UPDATE.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());
    }

    @Test
    void handleGetStageNullMenuCallbackTest() throws URISyntaxException, IOException {

        String json = Files.readString(
                Paths.get(UpdateServiceTest.class.getResource("callback_update.json").toURI()));
        Update updateButtonOne = getUpdate(json, "M0B1Callback");
        Update updateButtonTwo = getUpdate(json, "M0B2Callback");
        Update updateButtonThree = getUpdate(json, "M0B3Callback");
        Update updateButtonWrong = getUpdate(json, "WrongCallback");

        when(custodianService.findUserByChatId(any(Long.class))).thenReturn(true);

        updateService.editStatusMap(123L, BotStatus.STAGE_NULL_MENU);
        SendMessage message = updateService.updateHandler(updateButtonOne);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.STAGE_TWO_MENU.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_NULL_MENU);
        message = updateService.updateHandler(updateButtonTwo);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.STAGE_ONE_MENU.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_NULL_MENU);
        message = updateService.updateHandler(updateButtonThree);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.STAGE_SEND_REPORT_MENU_NULL.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_NULL_MENU);
        message = updateService.updateHandler(updateButtonWrong);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.UNHANDLED_UPDATE.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

    }

    @Test
    void handleGetStageOneMenuCallbackTest() throws URISyntaxException, IOException {

        String json = Files.readString(
                Paths.get(UpdateServiceTest.class.getResource("callback_update.json").toURI()));
        Update updateFirstButton = getUpdate(json, "M05B1Callback");
        Update updateSecondButton = getUpdate(json, "M05B2Callback");
        Update updateBackButton = getUpdate(json, "B1Callback");
        Update updateWrongButton = getUpdate(json, "WrongCallback");

        when(custodianService.findUserByChatId(any(Long.class))).thenReturn(true);

        updateService.editStatusMap(123L, BotStatus.STAGE_ONE_MENU);
        SendMessage message = updateService.updateHandler(updateFirstButton);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.STAGE_THREE_MENU.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_ONE_MENU);
        message = updateService.updateHandler(updateSecondButton);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.STAGE_THREE_MENU.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_ONE_MENU);
        message = updateService.updateHandler(updateBackButton);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.STAGE_NULL_MENU.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_ONE_MENU);
        message = updateService.updateHandler(updateWrongButton);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.UNHANDLED_UPDATE.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

    }

    @Test
    void handleGetStageTwoMenuCallbackTest() throws URISyntaxException, IOException {

        String json = Files.readString(
                Paths.get(UpdateServiceTest.class.getResource("callback_update.json").toURI()));
        Update updateBackButton = getUpdate(json, "B1Callback");

        when(custodianService.findUserByChatId(any(Long.class))).thenReturn(true);
        updateService.editStatusMap(123L, BotStatus.STAGE_TWO_MENU);

        SendMessage message = updateService.updateHandler(updateBackButton);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.STAGE_NULL_MENU.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

    }

    @Test
    void handleGetStageThreeMenuCallbacktest() throws URISyntaxException, IOException {

        String json = Files.readString(
                Paths.get(UpdateServiceTest.class.getResource("callback_update.json").toURI()));
        Update updateBackButton = getUpdate(json, "B1Callback");
        Update updateFirstDogButton = getUpdate(json, "M2B1CallbackDog");
        Update updateFirstCatButton = getUpdate(json, "M2B1CallbackCat");
        Update updateSecondButton = getUpdate(json, "M2B1Callback");
        Update updateThirdButton = getUpdate(json, "M2B13Callback");
        Update updateFourthDogButton = getUpdate(json, "M2B14Callback");
        Update updateFourthCatButton = getUpdate(json, "M2B15Callback");
        Update updateFifthDogButton = getUpdate(json, "M2B16Callback");
        Update updateFifthCatButton = getUpdate(json, "M2B17Callback");
        Update updateSixthDogButton = getUpdate(json, "M2B18Callback");
        Update updateSixthCatButton = getUpdate(json, "M2B19Callback");
        Update updateSeventhDogButton = getUpdate(json, "M2B20Callback");
        Update updateSeventhCatButton = getUpdate(json, "M2B21Callback");
        Update updateEighthDogButton = getUpdate(json, "M2B22Callback");
        Update updateEighthCatButton = getUpdate(json, "M2B23Callback");
        Update updateNinthButton = getUpdate(json, "M2B24Callback");
        Update updateWrongButton = getUpdate(json, "WrongCallback");


        when(custodianService.findUserByChatId(any(Long.class))).thenReturn(true);

        updateService.editStatusMap(123L, BotStatus.STAGE_THREE_MENU);
        SendMessage message = updateService.updateHandler(updateBackButton);
        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.STAGE_ONE_MENU.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_THREE_MENU);
        message = updateService.updateHandler(updateFirstDogButton);
        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.GETTING_TO_KNOW_A_DOG.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_THREE_MENU);
        message = updateService.updateHandler(updateFirstCatButton);
        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.GETTING_TO_KNOW_A_CAT.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_THREE_MENU);
        updateService.editStatusMenu(123L, "Dog");
        message = updateService.updateHandler(updateSecondButton);
        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.REQUIRED_DOCUMENTS.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_THREE_MENU);
        updateService.editStatusMenu(123L, "Cat");
        message = updateService.updateHandler(updateSecondButton);
        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.REQUIRED_DOCUMENTS.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_THREE_MENU);
        updateService.editStatusMenu(123L, "Dog");
        message = updateService.updateHandler(updateThirdButton);
        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.ANIMAL_TRANSPORTATION.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_THREE_MENU);
        updateService.editStatusMenu(123L, "Cat");
        message = updateService.updateHandler(updateThirdButton);
        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.ANIMAL_TRANSPORTATION.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_THREE_MENU);
        message = updateService.updateHandler(updateFourthDogButton);
        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.HOME_IMPROVEMENT_FOR_A_PUPPY.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_THREE_MENU);
        message = updateService.updateHandler(updateFourthCatButton);
        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.HOME_IMPROVEMENT_FOR_A_KITTEN.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_THREE_MENU);
        message = updateService.updateHandler(updateFifthDogButton);
        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.HOME_IMPROVEMENT_FOR_AN_ADULT_DOG.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_THREE_MENU);
        message = updateService.updateHandler(updateFifthCatButton);
        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.HOME_IMPROVEMENT_FOR_AN_ADULT_CAT.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_THREE_MENU);
        message = updateService.updateHandler(updateSixthDogButton);
        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.CARING_FOR_A_DOG_WITH_DISABILITIES.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_THREE_MENU);
        message = updateService.updateHandler(updateSixthCatButton);
        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.CARING_FOR_A_CAT_WITH_DISABILITIES.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_THREE_MENU);
        message = updateService.updateHandler(updateSeventhDogButton);
        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.TIPS_FROM_A_DOG_HANDLER.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_THREE_MENU);
        message = updateService.updateHandler(updateSeventhCatButton);
        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.FELINOLOGIST_ADVICE.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_THREE_MENU);
        message = updateService.updateHandler(updateEighthDogButton);
        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.PROVEN_DOG_HANDLERS.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_THREE_MENU);
        message = updateService.updateHandler(updateEighthCatButton);
        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.PROVEN_FELINOLOGISTS.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_THREE_MENU);
        updateService.editStatusMenu(123L, "Dog");
        message = updateService.updateHandler(updateNinthButton);
        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.REASONS_FOR_REFUSAL.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_THREE_MENU);
        updateService.editStatusMenu(123L, "Cat");
        message = updateService.updateHandler(updateNinthButton);
        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.REASONS_FOR_REFUSAL.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_THREE_MENU);
        message = updateService.updateHandler(updateWrongButton);
        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.UNHANDLED_UPDATE.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());
    }

    @Test
    void handleBackTheCatInfoTest() throws URISyntaxException, IOException {

        String json = Files.readString(
                Paths.get(UpdateServiceTest.class.getResource("callback_update.json").toURI()));
        Update updateBackButton = getUpdate(json, "B1CallbackCat");

        when(custodianService.findUserByChatId(any(Long.class))).thenReturn(true);
        updateService.editStatusMap(123L, BotStatus.CAT_BUTTON);
        Update updateWrongButton = getUpdate(json, "WrongCallback");


        SendMessage message = updateService.updateHandler(updateBackButton);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.STAGE_THREE_MENU.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.COMMON_BUTTON);
        updateService.editStatusMenu(123L, "Cat");

        message = updateService.updateHandler(updateBackButton);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.STAGE_THREE_MENU.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.COMMON_BUTTON);
        updateService.editStatusMenu(123L, "Cat");

        message = updateService.updateHandler(updateWrongButton);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.UNHANDLED_UPDATE.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());
    }

    @Test
    void handleBackTheDogInfoTest() throws URISyntaxException, IOException {

        String json = Files.readString(
                Paths.get(UpdateServiceTest.class.getResource("callback_update.json").toURI()));
        Update updateBackButton = getUpdate(json, "B1CallbackDog");
        Update updateWrongButton = getUpdate(json, "WrongCallback");

        when(custodianService.findUserByChatId(any(Long.class))).thenReturn(true);
        updateService.editStatusMap(123L, BotStatus.DOG_BUTTON);

        SendMessage message = updateService.updateHandler(updateBackButton);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.STAGE_THREE_MENU.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.COMMON_BUTTON);
        updateService.editStatusMenu(123L, "Dog");

        message = updateService.updateHandler(updateBackButton);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.STAGE_THREE_MENU.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.COMMON_BUTTON);
        updateService.editStatusMenu(123L, "Dog");

        message = updateService.updateHandler(updateWrongButton);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.UNHANDLED_UPDATE.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());
    }

    private Update getUpdate(String json, String replaced) {
        return BotUtils.fromJson(json.replace("%command%", replaced), Update.class);
    }
}