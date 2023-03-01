package pro.sky.java.course7.animal_shelter_bot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.java.course7.animal_shelter_bot.entity.TrialPeriod;

import java.util.Collection;

public interface TrialPeriodRepository extends JpaRepository<TrialPeriod, Long> {

    Collection<Long> findParentIdByVolunteer (String volunteer);
}
