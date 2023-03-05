package pro.sky.java.course7.animal_shelter_bot.service;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import pro.sky.java.course7.animal_shelter_bot.model.TrialPeriod;
import pro.sky.java.course7.animal_shelter_bot.model.UserCustodian;
import pro.sky.java.course7.animal_shelter_bot.repository.UserCustodianRepository;

import java.util.List;

@Service
public class CustodianService {
    UserCustodianRepository userCustodianRepository;

    public CustodianService(UserCustodianRepository userCustodianRepository) {
        this.userCustodianRepository = userCustodianRepository;
    }

    public UserCustodian createCustodian (UserCustodian custodian) {
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

}
