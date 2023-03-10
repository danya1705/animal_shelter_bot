package pro.sky.java.course7.animal_shelter_bot.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCustodian that = (UserCustodian) o;
        return userChatId == that.userChatId && id.equals(that.id) && Objects.equals(fullName, that.fullName) && Objects.equals(contacts, that.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userChatId, fullName, contacts);
    }
}
