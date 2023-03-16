package pro.sky.java.course7.animal_shelter_bot.service;

import org.springframework.stereotype.Service;
import pro.sky.java.course7.animal_shelter_bot.model.UserCustodian;
import pro.sky.java.course7.animal_shelter_bot.repository.UserCustodianRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustodianService {
    UserCustodianRepository userCustodianRepository;

    public CustodianService(UserCustodianRepository userCustodianRepository) {
        this.userCustodianRepository = userCustodianRepository;
    }

    public UserCustodian createCustodian(UserCustodian custodian) {
        return userCustodianRepository.save(custodian);
    }

    public UserCustodian editCustodian(UserCustodian custodian) {
        UserCustodian findCustodian = userCustodianRepository.findById(custodian.getId()).orElse(null);
        if (findCustodian != null) {
            return userCustodianRepository.save(custodian);
        }
        return null;
    }

    public List<UserCustodian> findAll() {
        return userCustodianRepository.findAll();
    }

    /**
     * Проверка на существование пользователя в БД
     */
    public Boolean findUserByChatId(Long chatId) {
        UserCustodian userCustodian = userCustodianRepository.findUserCustodianByUserChatId(chatId);
        if (userCustodian != null) {
            return userCustodian.getUserChatId() == chatId;
        } return false;
    }

    /**
     * Получение ID пользователя в базе по Telegram ID
     * @param chatId - Telegram ID пользоваеля
     * @return Long
     */
    public UserCustodian findUserCustodianByChatId(Long chatId) {
        return userCustodianRepository.findUserCustodianByUserChatId(chatId);
    }

    /**
     * Ищет chatId по userId
     * @param userId - Идентификатор пользователя в БД
     * @return Возвращает Optional со значением Телеграм-идентификатора пользователя
     */
    public Optional<Long> findChatIdByUserId(Long userId) {
        return userCustodianRepository.findById(userId)
                .map(UserCustodian::getUserChatId);
    }

    public Optional<UserCustodian> findExistingUser() {
        return Optional.ofNullable(userCustodianRepository.findAll().get(0));
    }

    public void deleteUserCustodian(Long id) {
        userCustodianRepository.deleteById(id);
    }
}
