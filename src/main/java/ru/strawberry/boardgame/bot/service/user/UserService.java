package ru.strawberry.boardgame.bot.service.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.strawberry.boardgame.bot.service.user.repository.UserRepository;
import ru.strawberry.boardgame.bot.service.user.repository.impl.UserRepositoryImpl;

import javax.transaction.Transactional;

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
        if(!userRepository.checkIfUserExists(tgId)) {
            userRepository.createUser(tgId);
        }
    }

    public boolean checkIfTeseraUserIsRegistered(Long tgId) {
        return userRepository.checkIfTeseraUserIsRegistered(tgId);
    }
}
