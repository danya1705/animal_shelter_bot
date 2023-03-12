package pro.sky.java.course7.animal_shelter_bot.model.keyboards;

import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

public class ButtonsKeyboard {
    private final String firstButton;
    private final String secondButton;
    private final String thirdButton;
    private final String fourthButton;

    public ButtonsKeyboard(String firstButton, String secondButton, String thirdButton, String fourthButton) {
        this.firstButton = firstButton;
        this.secondButton = secondButton;
        this.thirdButton = thirdButton;
        this.fourthButton = fourthButton;
    }

    public ReplyKeyboardMarkup setMarkup(){
        return new ReplyKeyboardMarkup(firstButton, secondButton)
                .addRow(thirdButton, fourthButton)
                .resizeKeyboard(true)
                .oneTimeKeyboard(true)
                .selective(true);
    }
}
