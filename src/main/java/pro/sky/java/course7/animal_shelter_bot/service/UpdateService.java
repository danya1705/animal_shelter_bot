package pro.sky.java.course7.animal_shelter_bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Contact;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Service;
import pro.sky.java.course7.animal_shelter_bot.model.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * Сервис для работы с обновлениями от Телеграм-бота
 */
@Service
public class UpdateService {
    private final KeyboardService keyboardService;
    private final VolunteerService volunteerService;
    private final TrialPeriodService trialPeriodService;
    private final CustodianService custodianService;
    private final ReportService reportService;
    private final TelegramBot telegramBot;
    /**
     * Ключ - идентификатор Телеграм-чата. Значение - статус общение клиент-бот для данного чата.
     */
    private final Map<Long, BotStatus> statusMap = new HashMap<>();
    /**
     * Интерфейс для хранения выбранного животного пользователем.
     * Ключ - идентификатор Телеграм-чата. Значение - выбранное животное (кошка, или собака).
     */
    private final Map<Long, AnimalType> statusMenu = new HashMap<>();
    private final Map<Long, Report> reportMap = new HashMap<>();

    public UpdateService(KeyboardService keyboardService, CustodianService custodianService, ReportService reportService,
                         VolunteerService volunteerService, TrialPeriodService trialPeriodService, TelegramBot telegramBot) {
        this.keyboardService = keyboardService;
        this.custodianService = custodianService;
        this.reportService = reportService;
        this.volunteerService = volunteerService;
        this.trialPeriodService = trialPeriodService;
        this.telegramBot = telegramBot;
    }

    public Map<Long, Report> getReportMap() {
        return reportMap;
    }

    /**
     * Обрабатывает обновления, получаемые Телеграм-ботом
     *
     * @param update не должен быть null
     * @return ответное сообщение для отправки в Телеграм-бот
     **/
    public SendMessage updateHandler(Update update) {

        Long chatId;
        if (update.message() != null) {
            chatId = update.message().chat().id();
            if (update.message().contact() != null) {
                if (!custodianService.findUserByChatId(chatId)) {
                    chatId = update.message().chat().id();
                    Contact contact = update.message().contact();
                    String name = contact.firstName() + " " + contact.lastName();
                    String phone = contact.phoneNumber();
                    UserCustodian custodian = new UserCustodian(chatId, name, phone);
                    custodianService.createCustodian(custodian);
                }
                return handlePrintGreetingsMessage(chatId);
            }
        } else if (update.callbackQuery() != null) {
            chatId = update.callbackQuery().from().id();
        } else {
            return null;
        }

        if (!custodianService.findUserByChatId(chatId)) {
            return handleUnregisteredUserMessage(chatId);
        } else {
            statusMap.putIfAbsent(chatId, BotStatus.GREETINGS_MESSAGE);
        }

        /*
         *  Статус, определяет логику действия бота, в зависимости от меню, в котором находится пользователь
         */
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
            case STAGE_THREE_MENU -> {
                if (update.callbackQuery() != null) {
                    String callbackData = update.callbackQuery().data();
                    return handleGetStageThreeMenuCallback(chatId, callbackData);
                }
            }
            case INFO_BUTTON -> {
                if (update.callbackQuery() != null) {
                    String callbackData = update.callbackQuery().data();
                    return handleBackInfoShelter(chatId, callbackData);
                }
            }
            case CAT_BUTTON -> {
                if (update.callbackQuery() != null) {
                    String callbackData = update.callbackQuery().data();
                    return handleBackTheCatInfo(chatId, callbackData);
                }
            }
            case DOG_BUTTON -> {
                if (update.callbackQuery() != null) {
                    String callbackData = update.callbackQuery().data();
                    return handleBackTheDogInfo(chatId, callbackData);
                }
            }
            case COMMON_BUTTON -> {
                if (update.callbackQuery() != null && statusMenu.get(chatId).equals(AnimalType.DOG)) {
                    String callbackData = update.callbackQuery().data();
                    return handleBackTheDogInfo(chatId, callbackData);
                }
                if (update.callbackQuery() != null && statusMenu.get(chatId).equals(AnimalType.CAT)) {
                    String callbackData = update.callbackQuery().data();
                    return handleBackTheCatInfo(chatId, callbackData);
                }
            }
            case STAGE_SEND_REPORT_MENU_NULL -> {
                if (update.callbackQuery() != null) {
                    String callbackData = update.callbackQuery().data();
                    return handleSendReportButtonMessage(chatId, callbackData);
                }
            }
            case STAGE_NULL_MENU_VOLUNTEER, STAGE_TWO_MENU_VOLUNTEER, STAGE_THREE_MENU_VOLUNTEER -> {
                if (update.callbackQuery() != null) {
                    String callbackData = update.callbackQuery().data();
                    return handleGetStageOneMenuVolunteerCallback(chatId, callbackData);
                }
            }
            case VOLUNTEER_CALLED_SUCCESSFULLY, FREE_VOLUNTEER_NOT_FOUND -> {
                if (update.callbackQuery() != null) {
                    String callbackData = update.callbackQuery().data();
                    return handleGetStageTwoMenuVolunteerCallback(chatId, callbackData);
                }
            }
            case STAGE_SEND_REPORT_MENU_PHOTO -> {
                if (update.callbackQuery() != null) {
                    String callbackData = update.callbackQuery().data();
                    return handlePhotoButtonReportMessage(chatId, callbackData);
                }
                if (update.message().photo() != null || update.message().text() != null) {
                    PhotoSize[] photo = update.message().photo();
                    return handlePhotoReportMessage(chatId, photo);
                }
            }
            case STAGE_SEND_REPORT_MENU_DIET -> {
                if (update.callbackQuery() != null) {
                    String callbackData = update.callbackQuery().data();
                    return handleDietReportMessageButton(chatId, callbackData);
                }
                if (update.message() != null) {
                    String text = update.message().text();
                    return handleDietReportMessage(chatId, text);
                }
            }
            case STAGE_SEND_REPORT_MENU_OVERALL_FEELINGS -> {
                if (update.callbackQuery() != null) {
                    String callbackData = update.callbackQuery().data();
                    return handleOverallReportMessageButton(chatId, callbackData);
                }
                if (update.message() != null) {
                    String text = update.message().text();
                    return handleOverallReportMessage(chatId, text);
                }
            }
            case STAGE_SEND_REPORT_MENU_CHANGES_IN_PET_LIFE -> {
                if (update.callbackQuery() != null) {
                    String callbackData = update.callbackQuery().data();
                    return handleChangesReportMessageButton(chatId, callbackData);
                }
                if (update.message() != null) {
                    String text = update.message().text();
                    return handleChangesReportMessage(chatId, text);
                }
            }
            case STAGE_SEND_REPORT_MENU_FINISH -> {
                if (update.callbackQuery() != null) {
                    String callbackData = update.callbackQuery().data();
                    return handleBackToMainReportMessage(chatId, callbackData);
                }
            }
        }
        return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
    }

    public SendMessage handleSendReportButtonMessage(Long chatId, String callbackData) {
        if (callbackData.equals(Buttons.REPORT_SEND_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_SEND_REPORT_MENU_PHOTO);
            return createMessage(chatId, BotStatus.STAGE_SEND_REPORT_MENU_PHOTO, keyboardService.stageAbortReportKeyboard());
        }
        if (callbackData.equals(Buttons.BACK_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_NULL_MENU);
            return createMessage(chatId, BotStatus.STAGE_NULL_MENU, keyboardService.stageNullMenuKeyboard());
        } else {
            return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
        }
    }

    public SendMessage handlePhotoButtonReportMessage(Long chatId, String callbackData) {
        if (callbackData.equals(Buttons.REPORT_SEND_ABORT_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_NULL_MENU);
            return createMessage(chatId, BotStatus.STAGE_NULL_MENU, keyboardService.stageNullMenuKeyboard());
        }
        if (callbackData.equals(Buttons.REPORT_SEND_CONFIRM_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_SEND_REPORT_MENU_DIET);
            return createMessage(chatId, BotStatus.STAGE_SEND_REPORT_MENU_DIET, keyboardService.stageAbortReportKeyboard());
        } else {
            return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
        }
    }

    public SendMessage handlePhotoReportMessage(Long chatId, PhotoSize[] photo) {
        if (photo != null) {
            String reportPhotoId = photo[photo.length - 1].fileId();

            Report report = new Report();
            report.setPhoto(reportPhotoId);
            reportMap.put(chatId, report);

            statusMap.put(chatId, BotStatus.STAGE_SEND_REPORT_MENU_DIET);
            return createMessage(chatId, BotStatus.STAGE_SEND_REPORT_MENU_DIET, keyboardService.stageAbortReportKeyboard());
        } else {
            return createMessage(chatId, "Отправить нужно только фотографию!", keyboardService.stageAbortReportKeyboard());
        }
    }

    public SendMessage handleDietReportMessageButton(Long chatId, String callbackData) {
        if (callbackData.equals(Buttons.REPORT_SEND_ABORT_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_NULL_MENU);
            return createMessage(chatId, BotStatus.STAGE_NULL_MENU, keyboardService.stageNullMenuKeyboard());
        } else {
            return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
        }
    }

    public SendMessage handleDietReportMessage(Long chatId, String text) {
        if (text != null) {
            reportMap.get(chatId).setDiet(text);
            statusMap.put(chatId, BotStatus.STAGE_SEND_REPORT_MENU_OVERALL_FEELINGS);
            return createMessage(chatId, BotStatus.STAGE_SEND_REPORT_MENU_OVERALL_FEELINGS, keyboardService.stageAbortReportKeyboard());
        } else {
            return createMessage(chatId, "Отправить нужно только текстовое описание меню питомца!"
                    , keyboardService.stageAbortReportKeyboard());
        }
    }

    public SendMessage handleOverallReportMessageButton(Long chatId, String callbackData) {
        if (callbackData.equals(Buttons.REPORT_SEND_ABORT_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_NULL_MENU);
            return createMessage(chatId, BotStatus.STAGE_NULL_MENU, keyboardService.stageNullMenuKeyboard());
        }
        if (callbackData.equals(Buttons.REPORT_SEND_CONFIRM_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_SEND_REPORT_MENU_CHANGES_IN_PET_LIFE);
            return createMessage(chatId, BotStatus.STAGE_SEND_REPORT_MENU_CHANGES_IN_PET_LIFE, keyboardService.stageAbortReportKeyboard());
        } else {
            return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
        }
    }

    public SendMessage handleOverallReportMessage(Long chatId, String text) {
        if (text != null) {
            reportMap.get(chatId).setBehaviour(text);
            statusMap.put(chatId, BotStatus.STAGE_SEND_REPORT_MENU_CHANGES_IN_PET_LIFE);
            return createMessage(chatId, BotStatus.STAGE_SEND_REPORT_MENU_CHANGES_IN_PET_LIFE, keyboardService.stageAbortReportKeyboard());
        } else {
            return createMessage(chatId, "Отправить нужно только текстовое описание самочувствия питомца!"
                    , keyboardService.stageAbortReportKeyboard());
        }
    }

    public SendMessage handleChangesReportMessageButton(Long chatId, String callbackData) {
        if (callbackData.equals(Buttons.REPORT_SEND_ABORT_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_NULL_MENU);
            return createMessage(chatId, BotStatus.STAGE_NULL_MENU, keyboardService.stageNullMenuKeyboard());
        } else {
            return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
        }
    }

    public SendMessage handleChangesReportMessage(Long chatId, String text) {
        if (text != null) {
            UserCustodian custodian = custodianService.findUserCustodianByChatId(chatId);
            reportMap.get(chatId).setWellbeing(text);
            reportMap.get(chatId).setReportDate(LocalDate.now());
            reportMap.get(chatId).setUserId(custodian.getId());

            statusMap.put(chatId, BotStatus.STAGE_SEND_REPORT_MENU_FINISH);

            reportService.createReport(reportMap.get(chatId));
            return createMessage(chatId, BotStatus.STAGE_SEND_REPORT_MENU_FINISH, keyboardService.backButtonKeyboard());
        } else {
            return createMessage(chatId, "Отправить нужно только текстовое описание об изменениях в поведении питомца!"
                    , keyboardService.stageAbortReportKeyboard());
        }
    }

    /**
     * Метод обработки нажатия кнопки "Назад".
     * Возвращает в предыдущее меню
     *
     * @param chatId       - Telegram ID пользователя
     * @param callbackData - полученный ответ кнопочного меню
     * @return выводит заданное меню и кнопки
     */
    public SendMessage handleBackToMainReportMessage(Long chatId, String callbackData) {
        if (callbackData.equals(Buttons.BACK_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_NULL_MENU);
            return createMessage(chatId, BotStatus.STAGE_NULL_MENU, keyboardService.stageNullMenuKeyboard());
        } else {
            return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
        }
    }

    /**
     * Метод обработки нажатия кнопки "Назад".
     * Возвращает в мены выбора информации по собакам
     *
     * @param chatId       - Telegram ID пользователя
     * @param callbackData - полученный ответ кнопочного меню
     * @return выводит заданное меню и кнопки
     */
    public SendMessage handleBackTheDogInfo(Long chatId, String callbackData) {
        if (callbackData.equals(Buttons.BACK_BUTTON_DOG.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_THREE_MENU);
            return createMessage(chatId, BotStatus.STAGE_THREE_MENU, keyboardService.stageThreeMenuDogKeyboard());
        } else {
            return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
        }
    }

    /**
     * Метод обработки нажатия кнопки "Назад".
     * Возвращает в мены выбора информации по кошкам
     *
     * @param chatId       - Telegram ID пользователя
     * @param callbackData - полученный ответ кнопочного меню
     * @return выводит заданное меню и кнопки
     */
    public SendMessage handleBackTheCatInfo(Long chatId, String callbackData) {
        if (callbackData.equals(Buttons.BACK_BUTTON_CAT.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_THREE_MENU);
            return createMessage(chatId, BotStatus.STAGE_THREE_MENU, keyboardService.stageThreeMenuCatKeyboard());
        } else {
            return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
        }
    }

    /**
     * Метод обработки нажатия кнопки "Назад".
     * Возвращает в мены выбора информации по приюту
     *
     * @param chatId       - Telegram ID пользователя
     * @param callbackData - полученный ответ кнопочного меню
     * @return выводит заданное меню и кнопки
     */
    public SendMessage handleBackInfoShelter(Long chatId, String callbackData) {
        if (callbackData.equals(Buttons.BACK_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_TWO_MENU);
            return createMessage(chatId, BotStatus.STAGE_TWO_MENU, keyboardService.stageTwoMenuKeyboard());
        } else {
            return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
        }
    }

    /**
     * Метод обработки нажатия кнопок в меню информации о животном
     *
     * @param chatId       - Telegram ID пользователя
     * @param callbackData - полученный ответ кнопочного меню
     * @return выводит заданное меню и кнопки
     */
    private SendMessage handleGetStageThreeMenuCallback(Long chatId, String callbackData) {

        if (callbackData.equals(Buttons.BACK_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_ONE_MENU);
            return createMessage(chatId, BotStatus.STAGE_ONE_MENU, keyboardService.stageOneMenuKeyboard());
        }
        if (callbackData.equals(Buttons.M2_FIRST_DOG_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.DOG_BUTTON);
            return createMessage(chatId, BotStatus.GETTING_TO_KNOW_A_DOG, keyboardService.backButtonKeyboardDog());
        }
        if (callbackData.equals(Buttons.M2_FIRST_CAT_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.CAT_BUTTON);
            return createMessage(chatId, BotStatus.GETTING_TO_KNOW_A_CAT, keyboardService.backButtonKeyboardCat());
        }
        if (callbackData.equals(Buttons.M2_SECOND_BUTTON.getCallback())) {
            if (statusMenu.get(chatId).equals(AnimalType.DOG)) {
                statusMap.put(chatId, BotStatus.COMMON_BUTTON);
                return createMessage(chatId, BotStatus.REQUIRED_DOCUMENTS, keyboardService.backButtonKeyboardDog());
            }
            if (statusMenu.get(chatId).equals(AnimalType.CAT)) {
                statusMap.put(chatId, BotStatus.COMMON_BUTTON);
                return createMessage(chatId, BotStatus.REQUIRED_DOCUMENTS, keyboardService.backButtonKeyboardCat());
            }
        }
        if (callbackData.equals(Buttons.M2_THIRD_BUTTON.getCallback())) {
            if (statusMenu.get(chatId).equals(AnimalType.DOG)) {
                statusMap.put(chatId, BotStatus.COMMON_BUTTON);
                return createMessage(chatId, BotStatus.ANIMAL_TRANSPORTATION, keyboardService.backButtonKeyboardDog());
            }
            if (statusMenu.get(chatId).equals(AnimalType.CAT)) {
                statusMap.put(chatId, BotStatus.COMMON_BUTTON);
                return createMessage(chatId, BotStatus.ANIMAL_TRANSPORTATION, keyboardService.backButtonKeyboardCat());
            }
        }
        if (callbackData.equals(Buttons.M2_FOURTH_DOG_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.DOG_BUTTON);
            return createMessage(chatId, BotStatus.HOME_IMPROVEMENT_FOR_A_PUPPY, keyboardService.backButtonKeyboardDog());
        }
        if (callbackData.equals(Buttons.M2_FOURTH_CAT_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.CAT_BUTTON);
            return createMessage(chatId, BotStatus.HOME_IMPROVEMENT_FOR_A_KITTEN, keyboardService.backButtonKeyboardCat());
        }
        if (callbackData.equals(Buttons.M2_FIFTH_DOG_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.DOG_BUTTON);
            return createMessage(chatId, BotStatus.HOME_IMPROVEMENT_FOR_AN_ADULT_DOG, keyboardService.backButtonKeyboardDog());
        }
        if (callbackData.equals(Buttons.M2_FIFTH_CAT_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.CAT_BUTTON);
            return createMessage(chatId, BotStatus.HOME_IMPROVEMENT_FOR_AN_ADULT_CAT, keyboardService.backButtonKeyboardCat());
        }
        if (callbackData.equals(Buttons.M2_SIXTH_DOG_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.DOG_BUTTON);
            return createMessage(chatId, BotStatus.CARING_FOR_A_DOG_WITH_DISABILITIES, keyboardService.backButtonKeyboardDog());
        }
        if (callbackData.equals(Buttons.M2_SIXTH_CAT_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.CAT_BUTTON);
            return createMessage(chatId, BotStatus.CARING_FOR_A_CAT_WITH_DISABILITIES, keyboardService.backButtonKeyboardCat());
        }
        if (callbackData.equals(Buttons.M2_SEVENTH_DOG_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.DOG_BUTTON);
            return createMessage(chatId, BotStatus.TIPS_FROM_A_DOG_HANDLER, keyboardService.backButtonKeyboardDog());
        }
        if (callbackData.equals(Buttons.M2_SEVENTH_CAT_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.CAT_BUTTON);
            return createMessage(chatId, BotStatus.FELINOLOGIST_ADVICE, keyboardService.backButtonKeyboardCat());
        }
        if (callbackData.equals(Buttons.M2_EIGHTH_DOG_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.DOG_BUTTON);
            return createMessage(chatId, BotStatus.PROVEN_DOG_HANDLERS, keyboardService.backButtonKeyboardDog());
        }
        if (callbackData.equals(Buttons.M2_EIGHTH_CAT_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.CAT_BUTTON);
            return createMessage(chatId, BotStatus.PROVEN_FELINOLOGISTS, keyboardService.backButtonKeyboardCat());
        }
        if (callbackData.equals(Buttons.M2_NINTH_BUTTON.getCallback())) {
            if (statusMenu.get(chatId).equals(AnimalType.DOG)) {
                statusMap.put(chatId, BotStatus.COMMON_BUTTON);
                return createMessage(chatId, BotStatus.REASONS_FOR_REFUSAL, keyboardService.backButtonKeyboardDog());
            }
            if (statusMenu.get(chatId).equals(AnimalType.CAT)) {
                statusMap.put(chatId, BotStatus.COMMON_BUTTON);
                return createMessage(chatId, BotStatus.REASONS_FOR_REFUSAL, keyboardService.backButtonKeyboardCat());
            }
        }
        if (callbackData.equals(Buttons.M2_ELEVENTH_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_THREE_MENU_VOLUNTEER);
            return createMessage(chatId, BotStatus.STAGE_THREE_MENU_VOLUNTEER, keyboardService.stageOneMenuVolunteerKeyboard());
        }
        return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
    }

    /**
     * Метод обработки нажатия кнопок в меню информации о приюте
     *
     * @param chatId       - Telegram ID пользователя
     * @param callbackData - полученный ответ кнопочного меню
     * @return выводит заданное меню и кнопки
     */

    private SendMessage handleGetStageTwoMenuCallback(Long chatId, String callbackData) {

        if (callbackData.equals(Buttons.BACK_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_NULL_MENU);
            return createMessage(chatId, BotStatus.STAGE_NULL_MENU, keyboardService.stageNullMenuKeyboard());
        }
        if (callbackData.equals(Buttons.M1_FIRST_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.INFO_BUTTON);
            return createMessage(chatId, BotStatus.INFORMATION_ABOUT_THE_SHELTER, keyboardService.backButtonKeyboard());
        }
        if (callbackData.equals(Buttons.M1_SECOND_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.INFO_BUTTON);
            return createMessage(chatId, BotStatus.ADDRESS_OF_THE_SHELTER, keyboardService.backButtonKeyboard());
        }
        if (callbackData.equals(Buttons.M1_THIRD_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.INFO_BUTTON);
            return createMessage(chatId, BotStatus.SAFETY_PRECAUTIONS, keyboardService.backButtonKeyboard());
        }
        if (callbackData.equals(Buttons.M1_FIFTH_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_TWO_MENU_VOLUNTEER);
            return createMessage(chatId, BotStatus.STAGE_TWO_MENU_VOLUNTEER, keyboardService.stageOneMenuVolunteerKeyboard());
        }
        return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
    }

    /**
     * Метод обработки нажатия кнопок в меню выбора животного.
     * Заносит значение в мапу, о выбранном животном
     *
     * @param chatId       - Telegram ID пользователя
     * @param callbackData - полученный ответ кнопочного меню
     * @return выводит заданное меню с информацией о выбранном животном и соответствующие кнопки
     */
    private SendMessage handleGetStageOneMenuCallback(Long chatId, String callbackData) {

        if (callbackData.equals(Buttons.M05_SECOND_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_THREE_MENU);
            statusMenu.put(chatId, AnimalType.DOG);
            return createMessage(chatId, BotStatus.STAGE_THREE_MENU, keyboardService.stageThreeMenuDogKeyboard());
        }
        if (callbackData.equals(Buttons.M05_FIRST_BUTTON.getCallback())) {
            statusMenu.put(chatId, AnimalType.CAT);
            statusMap.put(chatId, BotStatus.STAGE_THREE_MENU);
            return createMessage(chatId, BotStatus.STAGE_THREE_MENU, keyboardService.stageThreeMenuCatKeyboard());
        }
        if (callbackData.equals(Buttons.BACK_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_NULL_MENU);
            return createMessage(chatId, BotStatus.STAGE_NULL_MENU, keyboardService.stageNullMenuKeyboard());
        }
        return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
    }

    /**
     * Обработка меню: -Позвать волонтера
     */
    private SendMessage handleGetStageOneMenuVolunteerCallback(Long chatId, String callbackData) {

        if (callbackData.equals(Buttons.M11_FIRST_BUTTON.getCallback())) {

            Volunteer volunteer = volunteerService.callVolunteer(chatId);
            if (volunteer == null) {
                statusMap.put(chatId, BotStatus.FREE_VOLUNTEER_NOT_FOUND);
                return createMessage(chatId, BotStatus.FREE_VOLUNTEER_NOT_FOUND, keyboardService.stageTwoMenuVolunteerKeyboard());
            }

            Long volunteerChatId = volunteer.getChatId();
            UserCustodian custodian = custodianService.findUserCustodianByChatId(chatId);
            String custodianName = custodian.getFullName();
            String text = "<a href=" + " \"tg://user?id=" + chatId + "\"" + ">" + custodianName + "</a>" + " просил позвонить ему";
            SendMessage message = new SendMessage(volunteerChatId, text);
            message.parseMode(ParseMode.HTML);
            SendResponse response = telegramBot.execute(message);

            statusMap.put(chatId, BotStatus.VOLUNTEER_CALLED_SUCCESSFULLY);
            return createMessage(chatId, BotStatus.VOLUNTEER_CALLED_SUCCESSFULLY, keyboardService.stageTwoMenuVolunteerKeyboard());
        }

        if (callbackData.equals(Buttons.BACK_BUTTON.getCallback())) {

            BotStatus botStatus = statusMap.get(chatId);

            if (botStatus.equals(BotStatus.STAGE_NULL_MENU_VOLUNTEER)) {
                statusMap.put(chatId, BotStatus.STAGE_NULL_MENU);
                return createMessage(chatId, BotStatus.STAGE_NULL_MENU, keyboardService.stageNullMenuKeyboard());
            }
            if (botStatus.equals(BotStatus.STAGE_TWO_MENU_VOLUNTEER)) {
                statusMap.put(chatId, BotStatus.STAGE_TWO_MENU);
                return createMessage(chatId, BotStatus.STAGE_TWO_MENU, keyboardService.stageTwoMenuKeyboard());
            }
            if (botStatus.equals(BotStatus.STAGE_THREE_MENU_VOLUNTEER) && statusMenu.get(chatId).equals(AnimalType.DOG)) {
                statusMap.put(chatId, BotStatus.STAGE_THREE_MENU);
                return createMessage(chatId, BotStatus.STAGE_THREE_MENU, keyboardService.stageThreeMenuDogKeyboard());
            }
            if (botStatus.equals(BotStatus.STAGE_THREE_MENU_VOLUNTEER) && statusMenu.get(chatId).equals(AnimalType.CAT)) {
                statusMap.put(chatId, BotStatus.STAGE_THREE_MENU);
                return createMessage(chatId, BotStatus.STAGE_THREE_MENU, keyboardService.stageThreeMenuCatKeyboard());
            }
        }

        return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
    }

    private SendMessage handleGetStageTwoMenuVolunteerCallback(Long chatId, String callbackData) {
        if (callbackData.equals(Buttons.BACK_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_NULL_MENU);
            return createMessage(chatId, BotStatus.STAGE_NULL_MENU, keyboardService.stageNullMenuKeyboard());
        }
        return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
    }

    /**
     * Метод обработки нажатия кнопок в основном меню
     *
     * @param chatId       - Telegram ID пользователя
     * @param callbackData - полученный ответ кнопочного меню
     * @return выводит заданное меню и кнопки
     */
    private SendMessage handleGetStageNullMenuCallback(Long chatId, String callbackData) {

        if (callbackData.equals(Buttons.M0_FIRST_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_TWO_MENU);
            return createMessage(chatId, BotStatus.STAGE_TWO_MENU, keyboardService.stageTwoMenuKeyboard());
        }
        if (callbackData.equals(Buttons.M0_SECOND_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_ONE_MENU);
            return createMessage(chatId, BotStatus.STAGE_ONE_MENU, keyboardService.stageOneMenuKeyboard());
        }
        if (callbackData.equals(Buttons.M0_THIRD_BUTTON.getCallback())) {
            if (trialPeriodService.isPeriodByUserChatIdExists(custodianService
                    .findUserCustodianByChatId(chatId)
                    .getId())) {
                statusMap.put(chatId, BotStatus.STAGE_SEND_REPORT_MENU_NULL);
                return createMessage(chatId, BotStatus.STAGE_SEND_REPORT_MENU_NULL, keyboardService.stageNullReportKeyboard());
            } else {
                statusMap.put(chatId, BotStatus.STAGE_ONE_MENU);
                return createMessage(chatId, BotStatus.STAGE_SEND_REPORT_MENU_NO_TRIAL_PERIOD, keyboardService.stageOneMenuKeyboard());
            }
        }
        if (callbackData.equals(Buttons.M0_FOURTH_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_NULL_MENU_VOLUNTEER);
            return createMessage(chatId, BotStatus.STAGE_NULL_MENU_VOLUNTEER, keyboardService.stageOneMenuVolunteerKeyboard());
        }
        return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
    }

    /**
     * Метод нажатия кнопки "Старт". Запоминает пользователя.
     *
     * @param chatId       - Telegram ID пользователя
     * @param callbackData - полученный ответ кнопочного меню
     * @return выводит основное меню и кнопки
     */
    private SendMessage handleGetStartButtonCallback(Long chatId, String callbackData) {
        if (callbackData.equals(Buttons.START_BUTTON.getCallback())) {
            statusMap.put(chatId, BotStatus.STAGE_NULL_MENU);
            return createMessage(chatId, BotStatus.STAGE_NULL_MENU, keyboardService.stageNullMenuKeyboard());
        } else {
            return createMessage(chatId, BotStatus.UNHANDLED_UPDATE);
        }
    }

    /**
     * Метод приветственного сообщения
     *
     * @param chatId - Telegram ID пользователя
     * @return сообщение
     */
    private SendMessage handlePrintGreetingsMessage(Long chatId) {
        SendMessage message = createMessage(chatId, BotStatus.GREETINGS_MESSAGE, keyboardService.startButtonKeyboard());
        statusMap.put(chatId, BotStatus.START_BUTTON);
        return message;
    }

    /**
     * Метод приветственного сообщения, предупреждает, что пользователь не зарегистрирован.
     * Предлагает зарегистрироваться
     *
     * @param chatId - Telegram ID пользователя
     * @return сообщение
     */
    public SendMessage handleUnregisteredUserMessage(Long chatId) {
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

    public SendMessage createMessage(Long chatId, String text, InlineKeyboardMarkup markup) {
        SendMessage message = new SendMessage(chatId, text);
        return message.parseMode(ParseMode.HTML).replyMarkup(markup);
    }

    public void editStatusMap(Long chatId, BotStatus botStatus) {
        statusMap.put(chatId, botStatus);
    }

    public void editStatusMenu(Long chatId, AnimalType type) {
        statusMenu.put(chatId, type);
    }

    public void editReportMap(Long chatId, Report report) {
        reportMap.put(chatId, report);
    }

}
