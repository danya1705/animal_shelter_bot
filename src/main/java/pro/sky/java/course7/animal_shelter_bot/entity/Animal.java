package pro.sky.java.course7.animal_shelter_bot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String animalType;

    private String nickname;

    private Boolean availabilityAnimal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean getAvailabilityAnimal() {
        return availabilityAnimal;
    }

    public void setAvailabilityAnimal(Boolean availabilityAnimal) {
        this.availabilityAnimal = availabilityAnimal;
    }
}
