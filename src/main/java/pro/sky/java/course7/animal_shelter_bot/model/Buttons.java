package pro.sky.java.course7.animal_shelter_bot.model;

public enum Buttons {

    START_BUTTON("Старт", "S1Callback"),
    BACK_BUTTON ("Назад", "B1Callback"),
    M0_FIRST_BUTTON ("Информация о приюте", "M0B1Callback"),
    M0_SECOND_BUTTON ("Как взять питомца", "M0B2Callback"),
    M0_THIRD_BUTTON ("Отправить отчёт", "M0B3Callback"),
    M0_FOURTH_BUTTON ("Позвать волонтёра", "M0B4Callback"),
    M1_FIRST_BUTTON ("О приюте", "M1B1Callback"),
    M1_SECOND_BUTTON ("Адрес", "M1B2Callback"),
    M1_THIRD_BUTTON ("Техника безопасности", "M1B3Callback"),
    M1_FOURTH_BUTTON ("Оставить данные для связи", "M1B4Callback"),
    M1_FIFTH_BUTTON ("Позвать волонтёра", "M1B5ButtonCallback"),
    M2_FIRST_BUTTON ("Правила знакомства с собакой", "M2B1Callback"),
    M2_SECOND_BUTTON ("Список необходимых документов", "M2B1Callback"),
    M2_THIRD_BUTTON ("Транспортировка животного", "M2B1Callback"),
    M2_FOURTH_BUTTON ("Обустройство дома для щенка", "M2B1Callback"),
    M2_FIFTH_BUTTON ("Обустройство дома для взрослой собаки", "M2B1Callback"),
    M2_SIXTH_BUTTON ("Собаки с ограниченными возможностями", "M2B1Callback"),
    M2_SEVENTH_BUTTON ("Советы кинолога", "M2B1Callback"),
    M2_EIGHTH_BUTTON ("Проверенные кинологи", "M2B1Callback"),
    M2_NINTH_BUTTON ("Возможнве причины отказа", "M2B1Callback"),
    M2_TENTH_BUTTON ("Оставить контактные данные", "M2B1Callback"),
    M2_ELEVENTH_BUTTON ("Позвать волонтёра", "M2B1Callback");


    private final String text;
    private final String callback;

    Buttons(String text, String callback) {
        this.text = text;
        this.callback = callback;
    }

    public String getText() {
        return text;
    }

    public String getCallback() {
        return callback;
    }
}


