package pro.sky.java.course7.animal_shelter_bot.service;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.java.course7.animal_shelter_bot.model.BotStatus;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateServiceTest {

    @Mock
    private CustodianService custodianService;
    @Mock
    private KeyboardService keyboardService;
    @InjectMocks
    private UpdateService updateService;

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

        when(custodianService.findUserByChatId(any(Long.class))).thenReturn(true);
        updateService.editStatusMap(123L, BotStatus.START_BUTTON);

        SendMessage message = updateService.updateHandler(update);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.STAGE_NULL_MENU.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());
    }

    @Test
    void handleGetStageNullMenuCallbackTest() throws URISyntaxException, IOException {

        String json = Files.readString(
                Paths.get(UpdateServiceTest.class.getResource("callback_update.json").toURI()));
        Update updateButtonOne = getUpdate(json, "M0B1Callback");
        Update updateButtonTwo = getUpdate(json, "M0B2Callback");

        when(custodianService.findUserByChatId(any(Long.class))).thenReturn(true);
        updateService.editStatusMap(123L, BotStatus.STAGE_NULL_MENU);

        SendMessage message = updateService.updateHandler(updateButtonOne);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.STAGE_ONE_MENU.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

        updateService.editStatusMap(123L, BotStatus.STAGE_NULL_MENU);

        message = updateService.updateHandler(updateButtonTwo);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.STAGE_TWO_MENU.getMessageText());
        assertThat(message.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());

    }

    @Test
    void handleGetStageOneMenuCallbackTest() throws URISyntaxException, IOException {

        String json = Files.readString(
                Paths.get(UpdateServiceTest.class.getResource("callback_update.json").toURI()));
        Update updateBackButton = getUpdate(json, "B1Callback");

        when(custodianService.findUserByChatId(any(Long.class))).thenReturn(true);
        updateService.editStatusMap(123L, BotStatus.STAGE_ONE_MENU);

        SendMessage message = updateService.updateHandler(updateBackButton);

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(
                BotStatus.STAGE_NULL_MENU.getMessageText());
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

    private Update getUpdate(String json, String replaced) {
        return BotUtils.fromJson(json.replace("%command%", replaced), Update.class);
    }
}