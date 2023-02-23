package pro.sky.java.course7.animal_shelter_bot.service;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import pro.sky.java.course7.animal_shelter_bot.model.BotStatus;

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
    public Map<Long, BotStatus> statusMap = new HashMap<>();

    /**
     * Обрабатывает обновления, получаемые Телеграм-ботом
     * @param update не должен быть null
     * @return ответное сообщение для отправки в Телеграм-бот
     */
    public SendMessage updateHandler (Update update) {

        Long chatId = update.message().chat().id();
        statusMap.putIfAbsent(chatId, BotStatus.PRINT_GREETINGS_MESSAGE);
        BotStatus botStatus = statusMap.get(chatId);

        SendMessage message = new SendMessage(chatId, botStatus.getMessageText());
        message.parseMode(ParseMode.HTML);
//        if (menuMarkup(botStatus) != null) {
//            message = message.replyMarkup(menuMarkup(botStatus));
//        }

        return message;
    }
}
