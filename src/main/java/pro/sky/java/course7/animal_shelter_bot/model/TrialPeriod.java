package pro.sky.java.course7.animal_shelter_bot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class TrialPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate StartDate;
    private LocalDate EndDate;
    private Long volunteerId;
    private Long userId;
    private Long animalId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVolunteerId() {
        return volunteerId;
    }

    public void setVolunteerId(Long volunteerId) {
        this.volunteerId = volunteerId;
    }

    public LocalDate getStartDate() {
        return StartDate;
    }

    public void setStartDate(LocalDate startDate) {
        StartDate = startDate;
    }

    public LocalDate getEndDate() {
        return EndDate;
    }

    public void setEndDate(LocalDate endDate) {
        EndDate = endDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAnimalId() {
        return animalId;
    }

    public void setAnimalId(Long animalId) {
        this.animalId = animalId;
    }

    @Override
    public String toString() {
        return "TrialPeriod{" +
                "id=" + id +
                ", StartDate=" + StartDate +
                ", EndDate=" + EndDate +
                ", volunteerId=" + volunteerId +
                ", userId=" + userId +
                ", animalId=" + animalId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrialPeriod that = (TrialPeriod) o;
        return id.equals(that.id) && Objects.equals(StartDate, that.StartDate) && Objects.equals(EndDate, that.EndDate) && volunteerId.equals(that.volunteerId) && userId.equals(that.userId) && animalId.equals(that.animalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, StartDate, EndDate, volunteerId, userId, animalId);
    }
}
