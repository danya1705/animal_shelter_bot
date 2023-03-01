package pro.sky.java.course7.animal_shelter_bot.service;

import org.springframework.stereotype.Service;
import pro.sky.java.course7.animal_shelter_bot.entity.TrialPeriod;
import pro.sky.java.course7.animal_shelter_bot.repository.TrialPeriodRepository;

import java.util.List;

@Service
public class TrialPeriodService {
    TrialPeriodRepository trialPeriodRepository;

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
        TrialPeriod findTrialPeriod = trialPeriodRepository.findById(period.getId()).orElse(null);
        if (findTrialPeriod != null) {
            return trialPeriodRepository.save(period);
        }
        return null;
    }

    public void deletePeriod(long id) {
        trialPeriodRepository.deleteById(id);
    }
}