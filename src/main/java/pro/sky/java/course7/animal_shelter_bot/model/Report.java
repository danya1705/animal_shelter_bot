package pro.sky.java.course7.animal_shelter_bot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Arrays;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate reportDate;

    private String photo;

    private String diet;

    private String wellbeing;

    private String behaviour;

    private Long userId;

    public Report() {
    }

    public Report(LocalDate reportDate, String photo, String diet, String wellbeing, String behaviour, Long userId) {
        this.reportDate = reportDate;
        this.photo = photo;
        this.diet = diet;
        this.wellbeing = wellbeing;
        this.behaviour = behaviour;
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public void setReportDate(LocalDate reportDate) {
        this.reportDate = reportDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getWellbeing() {
        return wellbeing;
    }

    public void setWellbeing(String wellbeing) {
        this.wellbeing = wellbeing;
    }

    public String getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(String behaviour) {
        this.behaviour = behaviour;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Report{" +
                "id=" + id +
                ", reportDate=" + reportDate +
                ", photo=" + photo +
                ", diet='" + diet + '\'' +
                ", wellbeing='" + wellbeing + '\'' +
                ", behaviour='" + behaviour + '\'' +
                ", userId=" + userId +
                '}';
    }
}
