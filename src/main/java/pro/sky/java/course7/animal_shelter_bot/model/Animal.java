package pro.sky.java.course7.animal_shelter_bot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return id.equals(animal.id) && Objects.equals(animalType, animal.animalType) && Objects.equals(nickname, animal.nickname) && Objects.equals(availabilityAnimal, animal.availabilityAnimal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, animalType, nickname, availabilityAnimal);
    }
}
