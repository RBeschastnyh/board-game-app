package ru.strawberry.boardgame.web.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.strawberry.boardgame.web.data.dto.CreateUserRequest;
import ru.strawberry.boardgame.web.data.entity.User;
import ru.strawberry.boardgame.web.repository.UserRepository;
import ru.strawberry.boardgame.web.service.UserService;

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
        User user = User.builder()
                .login(request.getLogin())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .build();

        UUID uuid = userRepository.save(user);
        log.info("Created user {} with uuid {}", request.getLogin(), uuid);
    }
}
