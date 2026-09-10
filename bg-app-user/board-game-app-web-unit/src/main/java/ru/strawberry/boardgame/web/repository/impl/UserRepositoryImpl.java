package ru.strawberry.boardgame.web.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.strawberry.boardgame.web.data.entity.User;
import ru.strawberry.boardgame.web.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.UUID;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate postgresJdbcTemplate;

    @Autowired
    public UserRepositoryImpl(JdbcTemplate postgresJdbcTemplate) {
        this.postgresJdbcTemplate = postgresJdbcTemplate;
    }


    @Override
    public UUID save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        postgresJdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users (login, email, username, password) values (?, ?, ?, ?) returning id", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getUsername());
            preparedStatement.setString(4, user.getPassword());

            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKeyAs(UUID.class);
    }
}
