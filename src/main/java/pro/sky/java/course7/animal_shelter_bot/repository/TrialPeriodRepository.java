package pro.sky.java.course7.animal_shelter_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.java.course7.animal_shelter_bot.model.TrialPeriod;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


public interface TrialPeriodRepository extends JpaRepository<TrialPeriod, Long> {

    Collection<TrialPeriod> findAllByVolunteerId(Long volunteerId);
    List<TrialPeriod> findAllByEndDate(LocalDate endDate);
    TrialPeriod findTrialPeriodByUserId(Long id);

    @Query(value = """
    SELECT u.user_chat_id FROM trial_period t
    JOIN user_custodian u ON (t.user_id = u.id)
    WHERE t.id = ?1
    """, nativeQuery = true)
    Long  chatIdByByTrialPeriodId(Long id);

    @Query(value = """
    SELECT v.chat_id FROM trial_period t
    JOIN volunteer v ON (t.volunteer_id = v.id)
    WHERE t.id = ?1
    """, nativeQuery = true)
    Long chatIdByTrialPeriod(Long id);
}
