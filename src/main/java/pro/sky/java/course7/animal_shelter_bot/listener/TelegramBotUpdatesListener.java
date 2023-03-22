package pro.sky.java.course7.animal_shelter_bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.java.course7.animal_shelter_bot.service.KeyboardService;
import pro.sky.java.course7.animal_shelter_bot.service.TrialPeriodService;
import pro.sky.java.course7.animal_shelter_bot.service.UpdateService;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private final Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;
    private final UpdateService updateService;
    private final TrialPeriodService trialPeriodService;
    private final KeyboardService keyboardService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, UpdateService updateService, TrialPeriodService trialPeriodService
            , KeyboardService keyboardService) {
        this.telegramBot = telegramBot;
        this.updateService = updateService;
        this.trialPeriodService = trialPeriodService;
        this.keyboardService = keyboardService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.forEach(update -> {
                logger.info("Processing update: {}", update);
                if (update != null) {
                    SendResponse response = telegramBot.execute(updateService.updateHandler(update));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Scheduled(cron = "0 * 16 * * 1-5")
    public void sendTask() {
        System.out.println("Hey from SCHEDULE in Listener!!! ");
        trialPeriodService.findAllByEndDate(LocalDate.now())
                .forEach(trialPeriod -> {
                    sendMessageToChat(trialPeriodService.chatIdByTrialPeriod(trialPeriod.getId()),
                            "Испытательный срок №" + trialPeriod.getId() + " подходит к концу! Вынесите решение о продлении или закрытии в API.");
                });
        System.out.println(trialPeriodService.findAllByEndDate(LocalDate.now()));
    }

    public void sendMessageToChat(Long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        SendResponse response = telegramBot.execute(message);
    }
}
