package pro.sky.java.course7.animal_shelter_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.java.course7.animal_shelter_bot.model.Report;

import java.util.Collection;

public interface ReportRepository extends JpaRepository<Report,Long> {

    Collection<Report> findReportsByUserId (Long id);
}
