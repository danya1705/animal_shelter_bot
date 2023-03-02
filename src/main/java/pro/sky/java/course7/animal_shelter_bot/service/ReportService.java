package pro.sky.java.course7.animal_shelter_bot.service;

import org.springframework.stereotype.Service;
import pro.sky.java.course7.animal_shelter_bot.model.Report;
import pro.sky.java.course7.animal_shelter_bot.listener.TelegramBotUpdatesListener;
import pro.sky.java.course7.animal_shelter_bot.repository.ReportRepository;
import pro.sky.java.course7.animal_shelter_bot.repository.TrialPeriodRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final TrialPeriodRepository trialPeriodRepository;
    private final TelegramBotUpdatesListener telegramBotUpdatesListener;

    public ReportService(ReportRepository reportRepository, TrialPeriodRepository trialPeriodRepository, TelegramBotUpdatesListener telegramBotUpdatesListener) {
        this.reportRepository = reportRepository;
        this.trialPeriodRepository = trialPeriodRepository;
        this.telegramBotUpdatesListener = telegramBotUpdatesListener;
    }

    public List<Report> getReports(String volunteer, LocalDate dateFrom, LocalDate dateTo) {
        List<Report> reportList = new ArrayList<>();
        Collection<Long> idByVolunteer = trialPeriodRepository.findParentIdByVolunteer(volunteer);
        for (Long id : idByVolunteer) {
            reportList.addAll(reportRepository.findReportsByUserId(id));
        }
        return reportList.stream()
                .filter(e -> e.getReportDate().isAfter(dateFrom) && e.getReportDate().isBefore(dateTo))
                .collect(Collectors.toList());
    }

    public void sendMessage(Long id, String text) {
        telegramBotUpdatesListener.sendMessageToChat(id, text);
    }
}
