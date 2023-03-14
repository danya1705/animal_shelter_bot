package pro.sky.java.course7.animal_shelter_bot.model;

public enum Buttons {
    START_BUTTON("Старт", "S1Callback"),
    BACK_BUTTON ("Назад", "B1Callback"),
    M0_FIRST_BUTTON ("Информация о приюте", "M0B1Callback"),
    M0_SECOND_BUTTON ("Как взять питомца", "M0B2Callback"),
    M0_THIRD_BUTTON ("Отправить отчёт", "M0B3Callback"),
    M0_FOURTH_BUTTON ("Позвать волонтёра", "M0B4Callback"),
    M05_FIRST_BUTTON("Хочу взять кошку", "M05B1Callback"),
    M05_SECOND_BUTTON("Хочу взять собаку", "M05B2Callback"),
    M11_FIRST_BUTTON ("Звонок с волонтером", "M11B1Callback"),
    M11_SECOND_BUTTON ("Чат с волонтером", "M11B2Callback"),
    M1_FIRST_BUTTON ("О приюте", "M1B1Callback"),
    M1_SECOND_BUTTON ("Адрес", "M1B2Callback"),
    M1_THIRD_BUTTON ("Техника безопасности", "M1B3Callback"),
    M1_FOURTH_BUTTON ("Оставить данные для связи", "M1B4Callback"),
    M1_FIFTH_BUTTON ("Позвать волонтёра", "M1B5ButtonCallback"),
    M2_FIRST_DOG_BUTTON("Правила знакомства с собакой", "M2B1Callback"),
    M2_FIRST_CAT_BUTTON("Правила знакомства с кошкой", "M2B1Callback"),
    M2_SECOND_BUTTON ("Список необходимых документов", "M2B1Callback"),
    M2_THIRD_BUTTON ("Транспортировка животного", "M2B1Callback"),
    M2_FOURTH_DOG_BUTTON("Обустройство дома для щенка", "M2B1Callback"),
    M2_FOURTH_CAT_BUTTON("Обустройство дома для котенка", "M2B1Callback"),
    M2_FIFTH_DOG_BUTTON("Обустройство дома для взрослой собаки", "M2B1Callback"),
    M2_FIFTH_CAT_BUTTON("Обустройство дома для взрослой кошки", "M2B1Callback"),
    M2_SIXTH_DOG_BUTTON("Собаки с ограниченными возможностями", "M2B1Callback"),
    M2_SIXTH_CAT_BUTTON("Кошка с ограниченными возможностями", "M2B1Callback"),
    M2_SEVENTH_DOG_BUTTON("Советы кинолога", "M2B1Callback"),
    M2_SEVENTH_CAT_BUTTON("Советы фелинолога", "M2B1Callback"),
    M2_EIGHTH_DOG_BUTTON("Проверенные кинологи", "M2B1Callback"),
    M2_EIGHTH_CAT_BUTTON("Проверенные фелинологи", "M2B1Callback"),
    M2_NINTH_BUTTON ("Возможные причины отказа", "M2B1Callback"),
    M2_TENTH_BUTTON ("Оставить контактные данные", "M2B1Callback"),
    M2_ELEVENTH_BUTTON ("Позвать волонтёра", "M2B1Callback"),
    REPORT_SEND_BUTTON("Отправить отчет", "RSBCallback"),
    REPORT_SEND_CONFIRM_BUTTON("Отправил", "RSСBCallback"),
    REPORT_SEND_ABORT_BUTTON("Отмена", "RSABCallback");


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


