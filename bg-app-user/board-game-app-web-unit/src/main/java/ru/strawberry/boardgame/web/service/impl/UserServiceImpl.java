package ru.strawberry.boardgame.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.strawberry.boardgame.web.data.dto.CreateUserRequest;
import ru.strawberry.boardgame.web.data.dto.UpdateTeseraUsernameRequest;
import ru.strawberry.boardgame.web.data.entity.User;
import ru.strawberry.boardgame.web.exception.DuplicateEntityException;
import ru.strawberry.boardgame.web.repository.UserRepository;
import ru.strawberry.boardgame.web.service.UserService;

import java.util.Locale;
import java.util.UUID;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(CreateUserRequest request) {
        if (userRepository.checkUserExistsByLogin(request.getLogin())) {
            log.info("Attempting to create user {} while it exists", request.getLogin());

            throw new DuplicateEntityException("Логин занят");
        }

        User user = User.builder()
                .id(UUID.randomUUID())
                .login(request.getLogin().toLowerCase(Locale.ROOT))
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        UUID uuid = userRepository.save(user);
        log.info("Created user {} with uuid {}", request.getLogin(), uuid);
    }

    @Override
    public void updateTeseraUsername(UpdateTeseraUsernameRequest request) {
        log.info("updating tesera information for user {}, {}", request.getTeseraUsername(), request);
        if (request.getUsername() == null) {
            log.error("Username can not be null");
            throw new IllegalArgumentException("Пустое имя пользователя");
        }

        if (request.getTeseraUsername() == null) {
            log.error("Tesera username can not be null");
            throw new IllegalArgumentException("Пустой логин tesera");
        }

        try {
            this.userRepository.updateTeseraLogin(request.getUsername(), request.getTeseraUsername(), request.getTeseraUserId());
        } catch (Exception e) {
            log.error("Error occurred while updating tesera login", e);
        }

    }
}
