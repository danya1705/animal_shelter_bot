package pro.sky.java.course7.animal_shelter_bot.service;

import org.springframework.stereotype.Service;
import pro.sky.java.course7.animal_shelter_bot.exception.VolunteerNotFoundException;
import pro.sky.java.course7.animal_shelter_bot.model.Volunteer;
import pro.sky.java.course7.animal_shelter_bot.repository.VolunteerRepository;

import java.util.List;

@Service
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public List<Volunteer> getVolunteerAll() {
        return volunteerRepository.findAll();
    }

    public Volunteer getVolunteerById(long id) {
       return volunteerRepository.findById(id).orElse(null);
    }

    public Volunteer createVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public Volunteer deleteVolunteerId(Long id) {
        Volunteer volunteerDelete = volunteerRepository.findById(id).orElse(null);
        if (volunteerDelete != null) {
            volunteerRepository.deleteById(id);
        }
        return null;
    }

    public Volunteer updateVolunteer(Volunteer volunteer) {
        Volunteer findVolunteer = getVolunteerById(volunteer.getId());
        if (findVolunteer == null) {
            throw new VolunteerNotFoundException("Volunteer not found.");
        }
        return volunteerRepository.save(volunteer);
    }
}
