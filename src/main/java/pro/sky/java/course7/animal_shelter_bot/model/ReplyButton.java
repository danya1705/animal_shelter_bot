package pro.sky.java.course7.animal_shelter_bot.model;

public enum ReplyButton {
    SEND_CONTACT_BUTTON("Отправить контакт");

    String text;

    ReplyButton(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
