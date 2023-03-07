package pro.sky.java.course7.animal_shelter_bot.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;


//Сущьность, опекун животных.


@Entity
public class UserCustodian {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long userChatId;

    private String fullName;

    private String contacts;

    public UserCustodian() {

    }

    public UserCustodian(long userChatId, String fullName, String contacts) {
        this.userChatId = userChatId;
        this.fullName = fullName;
        this.contacts = contacts;
    }

    public long getUserChatId() {
        return userChatId;
    }

    public void setUserChatId(long userChatId) {
        this.userChatId = userChatId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }



}
