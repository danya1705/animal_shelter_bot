package pro.sky.java.course7.animal_shelter_bot.model;

/**
 * Класс-перечисление статусов общения клиент-бот
 */
public enum BotStatus {
    PRINT_GREETINGS_MESSAGE ("Здравствуйте! Вас приветствует приют для животных «Хатико». " +
            "Если вы задумываетесь о том, чтобы забрать собаку или кошку домой, жмите кнопку СТАРТ."),
    SHOW_STAGE_NULL_MENU ("Выберите интересующий вас пункт меню"),
    GET_STAGE_NULL_MENU_REPLY (""),
    SHOW_STAGE_ONE_MENU ("Выберите интересующий вас пункт меню"),
    SHOW_STAGE_TWO_MENU ("Выберите интересующий вас пункт меню"),
    GO_TO_REPORT_ENTERING ("");

    /**
     * Текст сообщения, отправляемого в бот
     */
    private final String messageText;

    BotStatus(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageText() {
        return messageText;
    }
}
