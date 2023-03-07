package pro.sky.java.course7.animal_shelter_bot.listener;

import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.sky.java.course7.animal_shelter_bot.model.BotStatus;
import pro.sky.java.course7.animal_shelter_bot.service.*;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelegramBotUpdatesListenerTest {

    @Captor
    ArgumentCaptor<SendMessage> messageCaptor;
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private UpdateService updateService;
    @InjectMocks
    private TelegramBotUpdatesListener telegramBotUpdatesListener;

    @Test
    public void shouldLaunchUpdateServiceCorrectlyAndCreatesCorrectResponseTest() throws URISyntaxException, IOException {

        SendMessage replyMessage = new SendMessage(123L, BotStatus.UNREGISTERED_USER_MESSAGE.getMessageText());
        replyMessage.parseMode(ParseMode.HTML);

        when(updateService.updateHandler(any(Update.class))).thenReturn(replyMessage);

        String json = Files.readString(
                Paths.get(TelegramBotUpdatesListenerTest.class.getResource("text_update.json").toURI()));
        Update update = getUpdate(json, "/start");
        telegramBotUpdatesListener.process(Collections.singletonList(update));

        verify(telegramBot).execute(messageCaptor.capture());
        SendMessage returnedMessage = messageCaptor.getValue();

        assertThat(returnedMessage.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(returnedMessage.getParameters().get("text")).isEqualTo(
                BotStatus.UNREGISTERED_USER_MESSAGE.getMessageText());
        assertThat(returnedMessage.getParameters().get("parse_mode"))
                .isEqualTo(ParseMode.HTML.name());
    }

    @Test
    void sendMessageTest() {

        Long chatId = 123L;
        String text = "Test text";

        telegramBotUpdatesListener.sendMessageToChat(chatId, text);

        verify(telegramBot).execute(messageCaptor.capture());
        SendMessage message = messageCaptor.getValue();

        assertThat(message.getParameters().get("chat_id")).isEqualTo(123L);
        assertThat(message.getParameters().get("text")).isEqualTo(text);

    }

    private Update getUpdate(String json, String replaced) {
        return BotUtils.fromJson(json.replace("%command%", replaced), Update.class);
    }

}