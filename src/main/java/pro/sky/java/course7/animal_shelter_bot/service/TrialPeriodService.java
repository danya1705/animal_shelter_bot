package pro.sky.java.course7.animal_shelter_bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import pro.sky.java.course7.animal_shelter_bot.model.TrialPeriod;
import pro.sky.java.course7.animal_shelter_bot.repository.TrialPeriodRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TrialPeriodService {
    private final TrialPeriodRepository trialPeriodRepository;
    private final CustodianService custodianService;
    private final TelegramBot telegramBot;

    public TrialPeriodService(TrialPeriodRepository trialPeriodRepository, CustodianService custodianService, TelegramBot telegramBot) {
        this.trialPeriodRepository = trialPeriodRepository;
        this.custodianService = custodianService;
        this.telegramBot = telegramBot;
    }

    public List<TrialPeriod> findAll() {
        return trialPeriodRepository.findAll();
    }

    public TrialPeriod createPeriod(TrialPeriod period) {
        return trialPeriodRepository.save(period);
    }

    public TrialPeriod editTrialPeriod(TrialPeriod period) {
        return trialPeriodRepository.findById(period.getId())
                .map(p -> trialPeriodRepository.save(period))
                .orElse(null);
    }

    public Optional<TrialPeriod> deletePeriod(long id) {
        Optional<TrialPeriod> findTrialPeriod = trialPeriodRepository.findById(id);
        sendMessage(trialPeriodRepository.chatIdByByTrialPeriodId(id), "Здравствуйте! Поздравляем, Ваш испытательный период успешно завершен!" +
                "Надеемся, питомец стал для вас настоящим членом семьи! Приходите еще и всего Вам доброго!");
        findTrialPeriod.ifPresent(t -> trialPeriodRepository.deleteById(id));
        return findTrialPeriod;
    }

    public boolean isPeriodByUserChatIdExists(Long id) {
        return trialPeriodRepository.findTrialPeriodByUserId(id) != null;
    }


    public List<TrialPeriod> findAllByEndDate(LocalDate now) {
        return trialPeriodRepository.findAllByEndDate(now);
    }

    public Long chatIdByTrialPeriod(Long id) {
        return trialPeriodRepository.chatIdByTrialPeriod(id);
    }

    public void extendTrialPeriodFor15Days(Long id) {
        TrialPeriod period = trialPeriodRepository.getById(id);
        period.setEndDate(period.getEndDate().plusDays(15));
        trialPeriodRepository.save(period);
        sendMessage(trialPeriodRepository.chatIdByByTrialPeriodId(id), "Здравствуйте, к сожалению, Ваш испытательный срок был продлен на 15 дней. " +
                "Уточнить подробности можно, связавшись с волонтером.");
    }

    public void extendTrialPeriodFor30Days(Long id) {
        TrialPeriod period = trialPeriodRepository.getById(id);
        period.setEndDate(period.getEndDate().plusDays(30));
        trialPeriodRepository.save(period);
        sendMessage(trialPeriodRepository.chatIdByByTrialPeriodId(id), "Здравствуйте, к сожалению, Ваш испытательный срок был продлен на 30 дней. " +
                "Уточнить подробности можно, связавшись с волонтером.");
    }


    public void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage(chatId, text);
        SendResponse response = telegramBot.execute(message);
    }

}
