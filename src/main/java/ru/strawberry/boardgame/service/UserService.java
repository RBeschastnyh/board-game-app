package ru.strawberry.boardgame.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.strawberry.boardgame.repository.UserRepository;

import javax.transaction.Transactional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void registerUser(Long tgId) {
        log.trace("Start register user {}", tgId);
        if (!userRepository.checkIfUserExists(tgId)) {
            log.info("Adding user with telegram id {}", tgId);
            userRepository.createUser(tgId);
        }
    }

    public boolean checkIfTeseraUserIsRegistered(Long tgId) {
        log.info("Checking if telegram user {} has registered tesera account", tgId);
        return userRepository.checkIfTeseraUserIsRegistered(tgId);
    }
}
