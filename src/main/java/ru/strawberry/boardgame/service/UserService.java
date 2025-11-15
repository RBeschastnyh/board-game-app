package ru.strawberry.boardgame.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.strawberry.boardgame.repository.UserRepository;
import ru.strawberry.boardgame.repository.impl.UserRepositoryImpl;

import javax.transaction.Transactional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {

    private static UserService userService;

    private UserRepository userRepository;

    public static synchronized UserService getInstance() {
        if (userService == null) {
            userService = new UserService(
                    new UserRepositoryImpl()
            );
        }

        return userService;
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
