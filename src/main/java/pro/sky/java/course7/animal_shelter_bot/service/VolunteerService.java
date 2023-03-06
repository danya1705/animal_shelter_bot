package pro.sky.java.course7.animal_shelter_bot.service;

import org.springframework.stereotype.Service;
import pro.sky.java.course7.animal_shelter_bot.exception.VolunteerNotFoundException;
import pro.sky.java.course7.animal_shelter_bot.model.Volunteer;
import pro.sky.java.course7.animal_shelter_bot.repository.VolunteerRepositiory;


import java.util.List;

@Service
public class VolunteerService {

    private final VolunteerRepositiory volunteerRepositiory;

    public VolunteerService(VolunteerRepositiory volunteerRepositiory) {
        this.volunteerRepositiory = volunteerRepositiory;
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
        return null;
    }

    public Volunteer updateVolunteer(Volunteer volunteer) {
        Volunteer findVolunteer = getVolunteerById(volunteer.getId());
        if (findVolunteer == null) {
            throw new VolunteerNotFoundException("Volunteer not found.");
        }
        return volunteerRepositiory.save(volunteer);
    }
}
