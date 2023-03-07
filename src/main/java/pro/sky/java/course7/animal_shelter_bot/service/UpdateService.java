package pro.sky.java.course7.animal_shelter_bot.service;

import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import pro.sky.java.course7.animal_shelter_bot.model.BotStatus;
import pro.sky.java.course7.animal_shelter_bot.model.Buttons;
import pro.sky.java.course7.animal_shelter_bot.model.UserCustodian;

import java.util.HashMap;
import java.util.Map;

/**
 * Сервис для работы с обновлениями от Телеграм-бота
 */
@Service
public class UpdateService {

    private final KeyboardService keyboardService;

    private final CustodianService custodianService;
    /**
     * Ключ - идентификатор Телеграм-чата. Значение - статус общение клиент-бот для данного чата.
     */
    private final Map<Long, BotStatus> statusMap = new HashMap<>();


    public UpdateService(KeyboardService keyboardService, CustodianService custodianService) {
        this.keyboardService = keyboardService;
        this.custodianService = custodianService;
    }


    /**
     * Обрабатывает обновления, получаемые Телеграм-ботом
     *
     * @param update не должен быть null
     * @return ответное сообщение для отправки в Телеграм-бот
     */
    public SendMessage updateHandler(Update update) {

        Long chatId;
        if (update.message() != null) {
            chatId = update.message().chat().id();
            if (update.message().contact() != null) {
                chatId = update.message().chat().id();
                Contact contact = update.message().contact();
                String name = contact.firstName() + " " + contact.lastName();
                String phone = contact.phoneNumber();
                UserCustodian custodian = new UserCustodian(chatId, name, phone);
                custodianService.createCustodian(custodian);
                return handlePrintGreetingsMessage(chatId);
            }
        } else if (update.callbackQuery() != null) {
            chatId = update.callbackQuery().from().id();
        } else {
            return null;
        }

        if (!custodianService.findUserByChatId(chatId)) {
            System.out.println(chatId + " oops, there's no such user");
            return handleUnregisteredUserMessage(chatId);
        } else {
            statusMap.putIfAbsent(chatId, BotStatus.GREETINGS_MESSAGE);
        }

        BotStatus botStatus = statusMap.get(chatId);
        System.out.println(chatId + " " + botStatus);
        switch (botStatus) {
            case GREETINGS_MESSAGE -> {
                return handlePrintGreetingsMessage(chatId);
            }
            case START_BUTTON -> {
                if (update.callbackQuery() != null) {
                    String callbackData = update.callbackQuery().data();
                    return handleGetStartButtonCallback(chatId, callbackData);
                }
            }
            case STAGE_NULL_MENU -> {
                if (update.callbackQuery() != null) {
                    String callbackData = update.callbackQuery().data();
                    return handleGetStageNullMenuCallback(chatId, callbackData);
                }

            }
            case STAGE_ONE_MENU -> {
                if (update.callbackQuery() != null) {
                    String callbackData = update.callbackQuery().data();
                    return handleGetStageOneMenuCallback(chatId, callbackData);
                }
            }
            case STAGE_TWO_MENU -> {
                if (update.callbackQuery() != null) {
                    String callbackData = update.callbackQuery().data();
                    return handleGetStageTwoMenuCallback(chatId, callbackData);
                }
            }
        }

        return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
    }

    private SendMessage handleGetStageTwoMenuCallback(Long chatId, String callbackData) {

        if (callbackData.equals(Buttons.BACK_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_ONE_MENU);
            return createMessage(chatId, BotStatus.STAGE_NULL_MENU, keyboardService.stageNullMenuKeyboard());
        }

        return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
    }

    private SendMessage handleGetStageOneMenuCallback(Long chatId, String callbackData) {

        if (callbackData.equals(Buttons.BACK_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_NULL_MENU);
            return createMessage(chatId, BotStatus.STAGE_NULL_MENU, keyboardService.stageNullMenuKeyboard());
        }

        return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
    }

    private SendMessage handleGetStageNullMenuCallback(Long chatId, String callbackData) {

        if (callbackData.equals(Buttons.M0_FIRST_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_ONE_MENU);
            return createMessage(chatId, BotStatus.STAGE_ONE_MENU, keyboardService.stageOneMenuKeyboard());
        }

        if (callbackData.equals(Buttons.M0_SECOND_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_TWO_MENU);
            return createMessage(chatId, BotStatus.STAGE_TWO_MENU, keyboardService.stageTwoMenuKeyboard());
        }

        return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
    }

    private SendMessage handleGetStartButtonCallback(Long chatId, String callbackData) {
        if (callbackData.equals(Buttons.START_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_NULL_MENU);
            return createMessage(chatId, BotStatus.STAGE_NULL_MENU, keyboardService.stageNullMenuKeyboard());
        } else {
            return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
        }
    }

    private SendMessage handlePrintGreetingsMessage(Long chatId) {
        SendMessage message = createMessage(chatId, BotStatus.GREETINGS_MESSAGE, keyboardService.startButtonKeyboard());
        statusMap.put(chatId, BotStatus.START_BUTTON);
        return message;
    }

    private SendMessage handleUnregisteredUserMessage(Long chatId) {
        SendMessage message = createMessage(chatId, BotStatus.UNREGISTERED_USER_MESSAGE, keyboardService.sendContactKeyboard());
        statusMap.put(chatId, BotStatus.START_BUTTON);
        return message;
    }

    public SendMessage createMessage(Long chatId, BotStatus botStatus) {
        SendMessage message = new SendMessage(chatId, botStatus.getMessageText());
        return message.parseMode(ParseMode.HTML);
    }

    public SendMessage createMessage(Long chatId, BotStatus botStatus, InlineKeyboardMarkup markup) {
        SendMessage message = new SendMessage(chatId, botStatus.getMessageText());
        return message.parseMode(ParseMode.HTML).replyMarkup(markup);
    }

    public SendMessage createMessage(Long chatId, BotStatus botStatus, ReplyKeyboardMarkup markup) {
        SendMessage message = new SendMessage(chatId, botStatus.getMessageText());
        return message.parseMode(ParseMode.HTML).replyMarkup(markup);
    }


}
