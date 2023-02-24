package pro.sky.java.course7.animal_shelter_bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.pengrad.telegrambot.UpdatesListener;
import org.springframework.scheduling.annotation.Scheduled;
import pro.sky.java.course7.animal_shelter_bot.model.BotStatus;
import pro.sky.java.course7.animal_shelter_bot.repository.NotificationTaskRepository;

import java.util.List;

public class TelegramBotUpdatesListener implements UpdatesListener {

    private final BotStatus botStatus;

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;

    private NotificationTaskRepository notificationTaskRepository;

    public TelegramBotUpdatesListener(BotStatus botStatus, TelegramBot telegramBot, NotificationTaskRepository notificationTaskRepository) {
        this.botStatus = botStatus;
        this.telegramBot = telegramBot;
        this.notificationTaskRepository = notificationTaskRepository;
    }


    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }


    @Override
    public int process(List<Update> list) {

        return 0;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public void sendNotificationTasks() {

    }

}
