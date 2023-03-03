package pro.sky.java.course7.animal_shelter_bot.service;

import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import org.springframework.stereotype.Service;
import pro.sky.java.course7.animal_shelter_bot.model.Buttons;

@Service
public class KeyboardService {

    public InlineKeyboardMarkup startButtonKeyboard() {

        InlineKeyboardButton[] buttons = new InlineKeyboardButton[1];
        buttons[0] = new InlineKeyboardButton(Buttons.START_BUTTON.getText())
                .callbackData(Buttons.START_BUTTON.getCallback());
        return new InlineKeyboardMarkup(buttons);
    }

    public InlineKeyboardMarkup backButtonKeyboard() {

        InlineKeyboardButton[] buttons = new InlineKeyboardButton[1];
        buttons[0] = new InlineKeyboardButton(Buttons.BACK_BUTTON.getText())
                .callbackData(Buttons.BACK_BUTTON.getCallback());
        return new InlineKeyboardMarkup(buttons);
    }

    public InlineKeyboardMarkup stageNullMenuKeyboard() {

        InlineKeyboardButton[][] buttons = new InlineKeyboardButton[2][2];

        buttons[0] = new InlineKeyboardButton[]{
                new InlineKeyboardButton(Buttons.M0_FIRST_BUTTON.getText())
                        .callbackData(Buttons.M0_FIRST_BUTTON.getCallback()),
                new InlineKeyboardButton(Buttons.M0_SECOND_BUTTON.getText())
                        .callbackData(Buttons.M0_SECOND_BUTTON.getCallback())
        };
        buttons[1] = new InlineKeyboardButton[]{
                new InlineKeyboardButton(Buttons.M0_THIRD_BUTTON.getText())
                        .callbackData(Buttons.M0_THIRD_BUTTON.getCallback()),
                new InlineKeyboardButton(Buttons.M0_FOURTH_BUTTON.getText())
                        .callbackData(Buttons.M0_FOURTH_BUTTON.getCallback())
        };

        return new InlineKeyboardMarkup(buttons);
    }

    public InlineKeyboardMarkup stageOneMenuKeyboard() {

        InlineKeyboardButton[][] buttons = new InlineKeyboardButton[3][2];

        buttons[0] = new InlineKeyboardButton[]{
                new InlineKeyboardButton(Buttons.M1_FIRST_BUTTON.getText())
                        .callbackData(Buttons.M1_FIRST_BUTTON.getCallback()),
                new InlineKeyboardButton(Buttons.M1_SECOND_BUTTON.getText())
                        .callbackData(Buttons.M1_SECOND_BUTTON.getCallback())
        };
        buttons[1] = new InlineKeyboardButton[]{
                new InlineKeyboardButton(Buttons.M1_THIRD_BUTTON.getText())
                        .callbackData(Buttons.M1_THIRD_BUTTON.getCallback()),
                new InlineKeyboardButton(Buttons.M1_FOURTH_BUTTON.getText())
                        .callbackData(Buttons.M1_FOURTH_BUTTON.getCallback())
        };
        buttons[2] = new InlineKeyboardButton[]{
                new InlineKeyboardButton(Buttons.M1_FIFTH_BUTTON.getText())
                        .callbackData(Buttons.M1_FIFTH_BUTTON.getCallback()),
                new InlineKeyboardButton(Buttons.BACK_BUTTON.getText())
                        .callbackData(Buttons.BACK_BUTTON.getCallback())
        };

        return new InlineKeyboardMarkup(buttons);
    }

    public InlineKeyboardMarkup stageTwoMenuKeyboard() {

        InlineKeyboardButton[][] buttons = new InlineKeyboardButton[12][1];

        buttons[0] = new InlineKeyboardButton[]{
                new InlineKeyboardButton(Buttons.M2_FIRST_BUTTON.getText())
                        .callbackData(Buttons.M2_FIRST_BUTTON.getCallback())
        };
        buttons[1] = new InlineKeyboardButton[]{
                new InlineKeyboardButton(Buttons.M2_SECOND_BUTTON.getText())
                        .callbackData(Buttons.M2_SECOND_BUTTON.getCallback())
        };
        buttons[2] = new InlineKeyboardButton[]{
                new InlineKeyboardButton(Buttons.M2_THIRD_BUTTON.getText())
                        .callbackData(Buttons.M2_THIRD_BUTTON.getCallback())
        };
        buttons[3] = new InlineKeyboardButton[]{
                new InlineKeyboardButton(Buttons.M2_FOURTH_BUTTON.getText())
                        .callbackData(Buttons.M2_FOURTH_BUTTON.getCallback())
        };
        buttons[4] = new InlineKeyboardButton[]{
                new InlineKeyboardButton(Buttons.M2_FIFTH_BUTTON.getText())
                        .callbackData(Buttons.M2_FIFTH_BUTTON.getCallback())
        };
        buttons[5] = new InlineKeyboardButton[]{
                new InlineKeyboardButton(Buttons.M2_SIXTH_BUTTON.getText())
                        .callbackData(Buttons.M2_SIXTH_BUTTON.getCallback())
        };
        buttons[6] = new InlineKeyboardButton[]{
                new InlineKeyboardButton(Buttons.M2_SEVENTH_BUTTON.getText())
                        .callbackData(Buttons.M2_SEVENTH_BUTTON.getCallback())
        };
        buttons[7] = new InlineKeyboardButton[]{
                new InlineKeyboardButton(Buttons.M2_EIGHTH_BUTTON.getText())
                        .callbackData(Buttons.M2_EIGHTH_BUTTON.getCallback())
        };
        buttons[8] = new InlineKeyboardButton[]{
                new InlineKeyboardButton(Buttons.M2_NINTH_BUTTON.getText())
                        .callbackData(Buttons.M2_NINTH_BUTTON.getCallback())
        };
        buttons[9] = new InlineKeyboardButton[]{
                new InlineKeyboardButton(Buttons.M2_TENTH_BUTTON.getText())
                        .callbackData(Buttons.M2_TENTH_BUTTON.getCallback())
        };
        buttons[10] = new InlineKeyboardButton[]{
                new InlineKeyboardButton(Buttons.M2_ELEVENTH_BUTTON.getText())
                        .callbackData(Buttons.M2_ELEVENTH_BUTTON.getCallback())
        };
        buttons[11] = new InlineKeyboardButton[]{
                new InlineKeyboardButton(Buttons.BACK_BUTTON.getText())
                        .callbackData(Buttons.BACK_BUTTON.getCallback())
        };

        return new InlineKeyboardMarkup(buttons);
    }

}
