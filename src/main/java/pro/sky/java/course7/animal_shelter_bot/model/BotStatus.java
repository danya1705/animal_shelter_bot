package pro.sky.java.course7.animal_shelter_bot.model;

/**
 * Класс-перечисление статусов общения клиент-бот
 */
public enum BotStatus {
    GREETINGS_MESSAGE("Здравствуйте! Вас приветствует приют для животных «Хатико». " +
            "Если вы задумываетесь о том, чтобы забрать собаку или кошку домой, жмите кнопку СТАРТ."),
    START_BUTTON(""),
    STAGE_NULL_MENU("Вы находитесь в основном меню. Выберите интересующий вас пункт."),
    STAGE_ONE_MENU("Вы находитесь в меню информации о приюте. Выберите интересующий вас пункт."),
    STAGE_TWO_MENU("Мы готовы помочь потенциальным «усыновителям» собаки из приюта разобраться " +
            "с бюрократическими (как оформить договор) и бытовыми (как подготовиться к жизни с собакой) " +
            "вопросами. Выберите интересующий вас пункт."),
    UNHANDLED_UPDATE("");

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
