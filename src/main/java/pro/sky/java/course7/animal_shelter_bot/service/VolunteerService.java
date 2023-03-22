package pro.sky.java.course7.animal_shelter_bot.service;

import org.springframework.stereotype.Service;
import pro.sky.java.course7.animal_shelter_bot.exception.VolunteerNotFoundException;
import pro.sky.java.course7.animal_shelter_bot.model.Volunteer;
import pro.sky.java.course7.animal_shelter_bot.repository.VolunteerRepositiory;


import java.util.List;
import java.util.Random;

@Service
public class VolunteerService {

    private final VolunteerRepositiory volunteerRepositiory;
    private final TrialPeriodService trialPeriodService;

    public VolunteerService(VolunteerRepositiory volunteerRepositiory, TrialPeriodService trialPeriodService) {
        this.volunteerRepositiory = volunteerRepositiory;
        this.trialPeriodService = trialPeriodService;
    }

    public List<Volunteer> getVolunteerAll() {
        return volunteerRepositiory.findAll();
    }

    public Volunteer getVolunteerById(long id) {
        return volunteerRepositiory.findById(id).orElse(null);
    }

    public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepositiory.save(volunteer);
    }

    public Volunteer deleteVolunteerId(Long id) {
        Volunteer volunteerDelete = volunteerRepositiory.findById(id).orElse(null);
        if (volunteerDelete != null) {
            volunteerRepositiory.deleteById(id);
        }
        return volunteerDelete;
    }

    public Volunteer updateVolunteer(Volunteer volunteer) {
        Volunteer findVolunteer = getVolunteerById(volunteer.getId());
        if (findVolunteer == null) {
            throw new VolunteerNotFoundException("Volunteer not found.");
        }
        return volunteerRepositiory.save(volunteer);
    }

    /**
     * Получить список свободных волонтеров
     */
    public List<Volunteer> getVolunteerAllFree() {
        return volunteerRepositiory.findVolunteersByAvailableTrue();
    }

    public Volunteer callVolunteer(Long chatID) {

        Long volunteerId = trialPeriodService.getVolunteerIdByUserChatId(chatID);
        if (volunteerId != null) {
            return getVolunteerById(volunteerId);
        } else {
            List<Volunteer> volunteers = getVolunteerAllFree();
            if (volunteers.size() > 0) {
                Random random = new Random();
                return volunteers.get(random.nextInt(volunteers.size()));
            } else {
                return null;
            }
        }
    }
}
