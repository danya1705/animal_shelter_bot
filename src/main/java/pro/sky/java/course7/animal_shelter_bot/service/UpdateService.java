package pro.sky.java.course7.animal_shelter_bot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import pro.sky.java.course7.animal_shelter_bot.model.BotStatus;
import pro.sky.java.course7.animal_shelter_bot.model.keyboards.ButtonsInlineKeyboard;
import pro.sky.java.course7.animal_shelter_bot.model.keyboards.ButtonsKeyboard;

import java.util.HashMap;
import java.util.Map;

/**
 * Сервис для работы с обновлениями от Телеграм-бота
 */
@Service
public class UpdateService {

    /**
     * Ключ - идентификатор Телеграм-чата. Значение - статус общение клиент-бот для данного чата.
     */
    private Map<Long, BotStatus> statusMap = new HashMap<>();

    private final ButtonsInlineKeyboard keyboardWithButtons = new ButtonsInlineKeyboard("Shelter's Info",
            "Take a pet",
            "Send report",
            "Call manager",
            "smtng",
            "smtng",
            "smtng",
            "smtng");

    private final ButtonsKeyboard buttonsKeyboard = new ButtonsKeyboard("Shelter's Info",
            "Take a pet",
            "Send report",
            "Call manager");

    /**
     * Обрабатывает обновления, получаемые Телеграм-ботом
     * @param update не должен быть null
     * @return ответное сообщение для отправки в Телеграм-бот
     */
    public SendMessage updateHandler (Update update) {

        Long chatId = update.message().chat().id();
        statusMap.putIfAbsent(chatId, BotStatus.PRINT_GREETINGS_MESSAGE);
        BotStatus botStatus = statusMap.get(chatId);

        switch (botStatus) {
            case PRINT_GREETINGS_MESSAGE -> {
                if ("/start".equals(update.message().text())) {
                    botStatus = BotStatus.SHOW_STAGE_NULL_MENU;
                }
            }
        }
        SendMessage message = new SendMessage(chatId, botStatus.getMessageText());
        message.parseMode(ParseMode.HTML).replyMarkup(keyboardWithButtons.setMarkup());

        return message;
    }
}
