package pro.sky.java.course7.animal_shelter_bot.model.keyboards;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;

public class ButtonsInlineKeyboard {
    private final InlineKeyboardButton[][] buttons = new InlineKeyboardButton[2][2];
    private final String firstButton;
    private final String secondButton;
    private final String thirdButton;
    private final String fourthButton;
    private final String firstCallbackData;
    private final String secondCallbackData;
    private final String thirdCallbackData;
    private final String fourthCallbackData;

    public ButtonsInlineKeyboard(String firstButton, String secondButton, String thirdButton, String fourthButton,
                                 String firstCallbackData, String secondCallbackData, String thirdCallbackData, String fourthCallbackData) {
        this.firstButton = firstButton;
        this.secondButton = secondButton;
        this.thirdButton = thirdButton;
        this.fourthButton = fourthButton;
        this.firstCallbackData = firstCallbackData;
        this.secondCallbackData = secondCallbackData;
        this.thirdCallbackData = thirdCallbackData;
        this.fourthCallbackData = fourthCallbackData;
    }

    public InlineKeyboardMarkup setMarkup(){
        buttons[0] = new InlineKeyboardButton[]{new InlineKeyboardButton(firstButton).callbackData(firstCallbackData),
                                                new InlineKeyboardButton(secondButton).callbackData(secondCallbackData)};
        buttons[1] = new InlineKeyboardButton[]{new InlineKeyboardButton(thirdButton).callbackData(thirdCallbackData),
                                                new InlineKeyboardButton(fourthButton).callbackData(fourthCallbackData)};
        return new InlineKeyboardMarkup(buttons);
    }

    public String getFirstCallbackData() {
        return firstCallbackData;
    }

    public String getSecondCallbackData() {
        return secondCallbackData;
    }

    public String getThirdCallbackData() {
        return thirdCallbackData;
    }

    public String getFourthCallbackData() {
        return fourthCallbackData;
    }
}
