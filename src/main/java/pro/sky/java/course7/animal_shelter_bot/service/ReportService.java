package pro.sky.java.course7.animal_shelter_bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.stereotype.Service;
import pro.sky.java.course7.animal_shelter_bot.model.Report;
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
    private final TelegramBot telegramBot;

    public ReportService(ReportRepository reportRepository, TrialPeriodRepository trialPeriodRepository, TelegramBot telegramBot) {
        this.reportRepository = reportRepository;
        this.trialPeriodRepository = trialPeriodRepository;
        this.telegramBot = telegramBot;
    }

    public List<Report> getReports(Long volunteerId, String stringDateFrom, String stringDateTo) {

        LocalDate dateFrom = LocalDate.parse(stringDateFrom);
        LocalDate dateTo = LocalDate.parse(stringDateTo);
        List<Report> reportList = new ArrayList<>();
        Collection<Long> idByVolunteer = trialPeriodRepository.findUserIdByVolunteerId(volunteerId);
        for (Long id : idByVolunteer) {
            reportList.addAll(reportRepository.findReportsByUserId(id));
        }
        return reportList.stream()
                .filter(e -> e.getReportDate().isAfter(dateFrom.minusDays(1))
                        && e.getReportDate().isBefore(dateTo.plusDays(1)))
                .collect(Collectors.toList());
    }

    public void sendMessage(Long id, String text) {
        SendMessage message = new SendMessage(id, text);
        SendResponse response = telegramBot.execute(message);
    }

    public Report createReport(Report report) {
        return reportRepository.save(report);
    }
}
