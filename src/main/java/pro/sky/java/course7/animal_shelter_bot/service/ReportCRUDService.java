package pro.sky.java.course7.animal_shelter_bot.service;

import org.springframework.stereotype.Service;
import pro.sky.java.course7.animal_shelter_bot.model.Report;
import pro.sky.java.course7.animal_shelter_bot.repository.ReportRepository;

@Service
public class ReportCRUDService {
    ReportRepository reportRepository;

    public ReportCRUDService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public Report createReport(Report report) {
        return reportRepository.save(report);
    }
}
