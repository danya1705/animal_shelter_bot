package pro.sky.java.course7.animal_shelter_bot.service;

import org.springframework.stereotype.Service;
import pro.sky.java.course7.animal_shelter_bot.model.TrialPeriod;
import pro.sky.java.course7.animal_shelter_bot.repository.TrialPeriodRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TrialPeriodService {
    private final TrialPeriodRepository trialPeriodRepository;

    public TrialPeriodService(TrialPeriodRepository trialPeriodRepository) {
        this.trialPeriodRepository = trialPeriodRepository;
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
        findTrialPeriod.ifPresent(t -> trialPeriodRepository.deleteById(id));
        return findTrialPeriod;
    }
}
