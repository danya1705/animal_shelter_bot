package pro.sky.java.course7.animal_shelter_bot.model;

/**
 * Класс-перечисление статусов общения клиент-бот
 */
public enum BotStatus {
    UNREGISTERED_USER_MESSAGE("Здравствуйте! К сожалению, Вы не зарегистрированы. Направьте нам Ваш контакт для дальнейшей работы," +
            " нажав на кнопку \"Отправить контакт\"."),
    GREETINGS_MESSAGE("Здравствуйте! Вас приветствует приют для животных «Хатико». " +
            "Если вы задумываетесь о том, чтобы забрать собаку или кошку домой, жмите кнопку СТАРТ."),
    START_BUTTON(""),
    STAGE_NULL_MENU("Вы находитесь в основном меню. Выберите интересующий вас пункт."),
    STAGE_ONE_MENU("Выберете какое животное вы хотите взять."),
    STAGE_ONE_MENU_VOLUNTEER("Найти свободного волонтера."),
    STAGE_TWO_MENU("Вы находитесь в меню информации о приюте. Выберите интересующий вас пункт."),
    INFORMATION_ABOUT_THE_SHELTER("Здесь будет информация о приюте"),
    ADDRESS_OF_THE_SHELTER("Здесь будет адрес приюта"),
    SAFETY_PRECAUTIONS("Здесь будет информация о технике безопасности"),
    STAGE_SEND_REPORT_MENU_VOLUNTEER("Свободный волонтер для консультации свяжется с вами в ближайшее время! \n" +
            "\n" +
            "Дла возврата в основное меню нажмите кнопку."),
    STAGE_THREE_MENU("Мы готовы помочь потенциальным «усыновителям» разобраться " +
            "с бюрократическими (как оформить договор) и бытовыми (как подготовиться к жизни c питомцем) " +
            "вопросами. Выберите интересующий вас пункт."),
    STAGE_SEND_REPORT_MENU_NULL("Вы находитесь в меню предоставления отчета.\n" +
            "Для перехода к заполнению отчета нажмите на кнопку <b>\"Отправить отчет\"</b>, и следуйте дальнейшим инструкциям. \n\n" +
            "Соблюдение требований инструкции обязательно!"),
    STAGE_SEND_REPORT_MENU_PHOTO("<i>Пункт 1:</i> Необходимо в ответ на эту операцию прислать <b>одну</b>" +
            " фотографию взятого питомца для оценки внешнего вида."),
    STAGE_SEND_REPORT_MENU_DIET("<i>Пункт 2:</i> Теперь в ответном сообщении на эту операцию опишите дневной рацион питомца."),
    STAGE_SEND_REPORT_MENU_OVERALL_FEELINGS("<i>Пункт 3:</i> Опишите в нескольких предложениях общее самочувствие питомца " +
            "и как проходит привыкание к новому месту"),
    STAGE_SEND_REPORT_MENU_CHANGES_IN_PET_LIFE("<i>Пункт 4:</i> Опишите изменения в поведении питомца: отказ от старых привычек, " +
            "приобретение новых."),
    STAGE_SEND_REPORT_MENU_FINISH("<i>Спасибо! Отчет внесен с базу данных!</i>"),
    UNHANDLED_UPDATE(""),
    COMMON_BUTTON(""),

    CAT_BUTTON(""),
    DOG_BUTTON(""),
    INFO_BUTTON(""),

    GETTING_TO_KNOW_A_DOG("1. Не навязывайте собаке своё общество. Нельзя забывать, что излишнее внимание и активное взаимодействие с питомцем принесёт ему лишний стресс, которого так важно избегать на первых порах. В идеале стоит дождаться момента, когда собака сама начнёт проявлять к вам интерес, сделает первый шаг. Когда питомец наконец пойдёт на контакт, рекомендуется поощрить это действие поглаживанием или лакомством.\n" +
                                  "2. Не мешайте животному самостоятельно исследовать новое окружение. Пусть собака обойдёт весь дом, понюхает каждый предмет и угол, убедится в том, что жилище для неё безопасно и не представляет угрозы.\n" +
                                  "3. Не устраивайте “смотрины” или какие-либо сборы гостей в первые недели жизни с животным. Собака может запаниковать и начать проявлять агрессию из-за внезапного появления шумных компаний незнакомых людей, а это явно не пойдёт на пользу процессу “одомашнивания”.\n" +
                                  "4. Не торопитесь ухаживать за собакой - кормить её, поить водой, гладить или затаскивать в ванну. Перед осуществлением этих действий нужно установить доверительные, непринуждённые отношения с животным. Советуем продемонстрировать питомцу миски для корма и воды и просто оставить их на своём месте. Через некоторое время, возможно, даже пару дней, собака сама начнёт есть из них. Не стойте над животным во время еды, не тревожьте его сон.\n" +
                                  "5. Если вы столкнулись со страхами собаки, действуйте спокойно и ласково, не напрягая своего питомца. Если собака начинает вести себя спокойно, поощряйте это поведение едой и поглаживаниями"),
    GETTING_TO_KNOW_A_CAT("Заранее поинтересуйтесь у куратора, какое лакомство предпочитает выбранное вами животное, и захватите «вкусняшку» с собой – с ее помощью будет проще наладить контакт.\n" +
            "Придя на место, не спешите брать кошку на руки. Лучше постойте в стороне, никак не показывая своей заинтересованности. Понаблюдайте, как животное ведет себя в более-менее привычной для него среде обитания: общается ли с другими кошками, не слишком ли агрессивно или чрезмерно пассивно.\n" +
            "Затем сделайте несколько шагов навстречу. Адекватный кот обратит на это внимание, заинтересуется и будет терпеливо ждать ваших дальнейших действий. Теперь можно подходить ближе, не переставая наблюдать, но избегая пристально смотреть в глаза кошке, так как животное может это воспринять как вызов.\n" +
            "Подождите, пока кошка сама подойдет к вам, а если этого не происходит, попробуйте приманить заранее припасенным лакомством. Рано или поздно любопытство возьмет верх, и кошка пойдет на контакт. Если животное не против, можете его погладить, сказать несколько ласковых слов. Для первой встречи этого вполне достаточно.\n" +
            "Бывает так, что все это запланированное постепенное знакомство совершенно не нужно: кошка сама без страха подходит, трется о ноги и просится на руки. Вы сразу понравились мурлыке.\n" +
            "После первого знакомства не спешите сразу забирать его из приюта. Вернитесь домой, еще раз хорошо обдумайте свое решение, обсудите его с другими членами семьи.\n" +
            "Если есть хоть малейшие сомнения, откажитесь от идеи завести питомца из приюта, ведь очередное предательство людей кот может просто не пережить. Но если вы уверены в своем выборе, сообщите куратору, когда сможете забрать выбранного кота или кошечку.\n"),
    REQUIRED_DOCUMENTS("Для того, чтобы забрать питомца из приюта нужен только паспорт. Данные вносятся в базу, по ним подготавливается договор и паспорт питомца."),

    ANIMAL_TRANSPORTATION("Транспортировать питомца нужно в специальном боксе. Так он будет в безопасности и дорога не покажется тяжелой. Так же можно уточнить у волонтера о транспортных компаниях, которые помогут вам с транспортировкой."),
    HOME_IMPROVEMENT_FOR_A_PUPPY("Спальное место для собаки – это первая территория жилища к которой должен привыкнуть щенок. Если не предоставить собаке личное место, это грозит «расшатыванием» нервной системы, порождением чувств неуверенности и ущемленности. Нюансы обустройства зависят от характера питомца и требований породы, а не от дизайна помещения или принципа «лишь бы не мешало». Нужно учесть повадки собаки и ее «взрослый» размер. \n" +
            "Глобально все модели спальных мест можно разделить на три вида:\n" +
            "Овальные с бортами – для собак, которые предпочитают спать калачиком. Как корпус может использоваться плетеная корзина, в которую помещается подстилка или матрац. \n" +
            "Лежак-матрац – для крупных пород. Возможны варианты с мягкой спинкой, одеялом и бортами. \n" +
            "Поролоновый домик подойдет для мелких или предпочитающих уединение питомцев. Сам лежак может быть пластиковый или деревянный, главное, чтобы он выдержал «натиск» взрослого питомца и не стал «домом» для инфекций и паразитов. \n" +
            "При выборе нужно обратить внимание на несколько аспектов: \n" +
            "Общие правила расположения и ухода за местом собаки \n" +
            "Заранее определите рамки поведения собаки. Если питомец не ограничен строгими правилами, может залезать на диваны или лежать на пороге, то расположение спального места хозяин может определить самостоятельно. \n" +
            "Общие принципы выбора места установки лежака следующие: \n" +
            "Отсутствие сквозняка. Удаленность от батарей, кондиционеров, аудиосистем и других климатических и электрических приборов. Не стоит располагать место вблизи от дверей или кухни. Территория должна быть уединенной, даже если в доме гости. Доступность для уборки. Нужно оборудовать альтернативный вариант на случай стирки или санитарной обработки лежака.\n"),

    HOME_IMPROVEMENT_FOR_A_KITTEN("Как правило, котятам гораздо легче адаптироваться к новому месту, чем взрослой кошке. Котенку стоит уделять больше внимания, покупать игрушки и не оставлять надолго в одиночестве.\n" +
            "Обратите внимание, куда прячется котенок при испуге, и старайтесь держать доступ в это место открытым, чтобы он мог спокойно спрятаться в случае опасности.\n" +
            "Разговаривать с малышом надо спокойно, не повышая голоса, даже когда будете приучать его к лотку и когтеточке. Приучать к рукам нужно постепенно. Лучше начать поглаживать котенка после кормления, когда он расслаблен и готов к контакту.\n" +
            "В первые дни адаптации котенок может испугаться музыки и громких звуков, об этом нужно предупредить всех членов семьи.\n" +
            "Возможно, котенок будет озабоченно шататься и ходить по квартире – он ищет маму и материнское молоко. Не кричите и не мешайте ему, он должен освоиться самостоятельно. Брать на ручки в случае, если маленький котик ещё не доверяет и стрессует, тоже не стоит. Запаситесь терпением, и вы ещё услышите от него ласковое и благодарное урчание!\n"),

    HOME_IMPROVEMENT_FOR_AN_ADULT_DOG("Сначала следует подготовить место для собаки. Желательно разместить всё необходимое в пределах одной комнаты. Скорее всего, питомец не захочет исследовать всё пространство сразу. Собаки чувствуют себя неуверенно при пребывании на незнакомой территории, поэтому сначала лучше ограничиться одной комнатой. Там должны быть миски, игрушки и лежанка. Другие двери не закрывайте, поскольку изоляция вызовет стресс. \n" +
            "В большинстве случаев питомцы после переезда на некоторое время забывают о правилах поведения. Желательно выделить собаке комнату с таким покрытием, которое будет проще чистить. \n" +
            "Рекомендуется заранее перекрыть доступ к хрупким и потенциально опасным предметам. Например, провода следует убрать. Если это невозможно, желательно обмотать их лентой или спрятать в коробки. Растения нужно перенести в другие комнаты. Необходимо убрать хрупкие и острые предметы со стола. Бытовую химию настоятельно рекомендуется переставить в закрытые шкафы или на высокие полки\n" +
            "До приезда собаки нужно провести воспитательную беседу с домочадцами. Объясните им, что воспитывать такого питомца будет вдвойне труднее. Члены семьи не должны допускать поблажки, иначе нежелательное поведение закрепится, но и не должны жестоко наказывать. Желательно заранее изготовить жетон с вашим адресом. В первые дни собака может пытаться сбежать. Адресник повысит её шансы на возвращение.\n"),

    HOME_IMPROVEMENT_FOR_AN_ADULT_CAT("Итак, вы выбрали кошку. Теперь ваша задача — обустроить пространство, в котором она будет жить. Обязательно учитывайте, что кошки очень любопытны. Нужно хорошо знать их повадки, желания и потребности, чтобы создать в доме не только комфортную, но и безопасную для них обстановку.\n" +
            "Чтобы кошка легче привыкала к дому, ей должно быть в нем уютно. Для этого нужно обустроить для нее места для игр и сна, не забывая о безопасности.\n" +
            "Какие меры предосторожности стоит соблюсти?\n" +
            "Уберите провода и шнуры (их можно спрятать в специальный короб или под плинтус).\n" +
            "Никогда не оставляйте окна, входную и балконную двери открытыми.\n" +
            "Уберите подальше иголки и нитки, елочные украшения, бьющиеся и хрупкие предметы, бытовую химию.\n" +
            "Не оставляйте включенными плиту и утюг.\n" +
            "Перед тем как включать стиральную машинку, проверяйте, не забралась ли кошка внутрь барабана.\n" +
            "Избавьтесь от ядовитых для кошек растений (среди них цикламен, плющ, шеффлера и другие).\n"),

    CARING_FOR_A_DOG_WITH_DISABILITIES("К счастью, много собак с ограниченными возможностями все еще могут вести счастливую жизнь при надлежащем уходе и внимании. \n" +
            "Прежде чем усыновить любую собаку-инвалида, убедитесь, что вы понимаете тип ухода, в котором нуждается ваша собака, чтобы вы могли посвятить некоторое время уходу за вашим другом. \n" +
            "Изучите правильные приемы, прежде чем везти свою собаку домой. Не у каждого есть время и ресурсы для ухода за собакой с особыми потребностями. А наши волонтеры подскажут что нужно, для собаки с ограниченными возможностями.\n"),
    CARING_FOR_A_CAT_WITH_DISABILITIES("К счастью, много кошек с ограниченными возможностями все еще могут вести счастливую жизнь при надлежащем уходе и внимании. \n" +
            "Прежде чем усыновить любую кошку-инвалида, убедитесь, что вы понимаете тип ухода, в котором нуждается ваша кошка, чтобы вы могли посвятить некоторое время уходу за вашим другом. \n" +
            "Изучите правильные приемы, прежде чем везти свою кошку домой. Не у каждого есть время и ресурсы для ухода за кошкой с особыми потребностями. А наши волонтеры подскажут что нужно, для кошки с ограниченными возможностями.\n"),
    TIPS_FROM_A_DOG_HANDLER("1. Будьте готовы к трудностям\n" +
            "Вы можете взять собаку любого возраста, но только при условии ясного понимания того, что чем старше собака, тем сложнее ей будет влиться в вашу семью. Чем богаче ее жизненный опыт, тем больше в нем может быть подводных камней.\n" +
            "2. Как можно подробней выясните историю собаки\n" +
            "Обязательно спросите у нынешних владельцев или кураторов, в каком возрасте собака попала в приют, сколько раз с этого времени меняла хозяев и почему от нее отказывались. Обратите внимание на наличие острых или хронических заболеваний, травм и последствий проведенных операций. \n" +
            "3. Внимательно оцените обстановку в доме, где жила собака\n" +
            "Возможность забрать собаку от первых владельцев - большая удача. У животного еще не возникло горькое послевкусие от смены семей, а его историю можно услышать из первых уст. Кстати, свежие следы повреждений на мебели, стенах или предметах обстановки или характерный запах собачьих испражнений дадут вам максимально точное представление о том, почему именно от собаки решили избавиться. \n" +
            "4. Посетите собаку несколько раз\n" +
            "В первую встречу собака скорее всего будет вести себя скованно, из-за чего у вас может сложиться неверное представление о ее характере и темпераменте. Идеальный вариант – пообщаться с собакой несколько раз хотя бы в течение недели. Будьте терпеливы и дайте животному раскрыться. \n" +
            "5. Обязательно сходите с собакой на прогулку\n" +
            "Если позволят обстоятельства и разрешит куратор или владелец, погуляйте с собакой один на один хотя бы полчаса. Пройдите с ней по улице, зайдите в парк и подойдите к ближайшему торговому центру. Посмотрите, как собака относится к другим животным, к детям, окружающим людям и городскому шуму. Оцените, насколько она готова идти с вами на контакт, согласна ли выполнять элементарные команды и принимать от вас угощение\n" +
            "6. Постарайтесь расположить к себе собаку\n" +
            "Лакомство это необходимое (но не достаточное) условие для того, чтобы установить дружеские отношения с собакой. Запаситесь угощением, предварительно уточнив у кураторов возможные противопоказания. Старайтесь избегать тактильных ласк – особенно если собака несколько раз меняла хозяев или страдала от побоев. Дождитесь, пока она сама решит к вам подойти. \n" +
            "7. Дайте собаке время на адаптацию в новом доме\n" +
            "Собака испытывает стресс вне зависимости от того, какой была ее жизнь до этого момента. Новый дом означает новые правила, общение с новыми людьми требует осторожности. Если желаете собаке добра, по приезду домой оставьте ее в покое, предоставив свободный доступ к воде и корму. Собаке потребуется от нескольких часов до нескольких дней, чтобы прийти в себя и оценить обстановку.\n" +
            "8. Наблюдайте за поведением собаки\n" +
            "Собака раскрывается в течение одного-двух месяцев. Заметив проблему, не паникуйте, а свяжитесь со специалистом. Так будет лучше для всех.\n" +
            "9. Будьте взрослыми\n" +
            "Собака, которую вы берете в приюте, наверняка не будет похожа ни на одну из ваших предыдущих. Будучи сложившейся личностью, она вряд ли согласится соответствовать образу, который вы себе нарисовали. Попробуйте загнать ее в рамки, и она быстро покажет, что вы и ваша семья не великие благодетели, а пока еще просто остановка на пути.\n"),
    FELINOLOGIST_ADVICE("Кормите питомца в первые 10 дней так же, как его кормили в приюте. Потом при необходимости можно постепенно начать переход на новый рацион.\n" +
            "Первые несколько дней после новоселья вам лучше побыть дома, чтобы усатый-полосатый к вам привык. Если в случае с котенком ситуацию лучше постоянно контролировать, то адаптация взрослой кошки в новом доме проходит не столь волнительно для хозяев. Ее можно предоставить самой себе на весьма долгий срок.\n" +
            "Пусть в распоряжении подопечного будут его любимые игрушки, домики, лежанки и когтеточка любимой формы. Это облегчит адаптацию.\n"),
    PROVEN_DOG_HANDLERS("Номера проверенных кинологов вы можете узнать у наших волонтеров"),
    PROVEN_FELINOLOGISTS("Номера проверенных фелинологов вы можете узнать у наших фелинологов"),
    REASONS_FOR_REFUSAL("Работники и волонтеры стараются сделать все, чтобы кошки и собаки не оказались на улице повторно, поэтому отдают животных только в надежные руки. Существует пять причин, по которым чаще всего отказывают желающим «усыновить» домашнего любимца.\n" +
            "1 Большое количество животных дома\n" +
            "2 Нестабильные отношения в семье\n" +
            "3 Наличие маленьких детей\n" +
            "4 Съемное жилье\n" +
            "5 Животное в подарок или для работы\n");




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
