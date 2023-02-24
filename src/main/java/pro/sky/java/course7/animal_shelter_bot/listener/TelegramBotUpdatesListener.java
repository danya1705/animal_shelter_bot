package pro.sky.java.course7.animal_shelter_bot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.java.course7.animal_shelter_bot.service.UpdateService;

import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final TelegramBot telegramBot;
    private final UpdateService updateService;

    public TelegramBotUpdatesListener(TelegramBot telegramBot, UpdateService updateService) {
        this.telegramBot = telegramBot;
        this.updateService = updateService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            if (update != null) {
                SendResponse response = telegramBot.execute(updateService.updateHandler(update));
            }
        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
