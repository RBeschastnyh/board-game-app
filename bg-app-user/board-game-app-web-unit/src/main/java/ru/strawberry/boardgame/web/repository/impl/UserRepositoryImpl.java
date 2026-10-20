package ru.strawberry.boardgame.web.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.strawberry.boardgame.web.data.entity.User;
import ru.strawberry.boardgame.web.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Locale;
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
    public UUID save(@NonNull User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        postgresJdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users (id, login, email, username, password) values (?, ?, ?, ?, ?) returning id", Statement.RETURN_GENERATED_KEYS);
            int counter = 1;
            preparedStatement.setObject(counter++, user.getId());
            preparedStatement.setString(counter++, user.getLogin());
            preparedStatement.setString(counter++, user.getEmail());
            preparedStatement.setString(counter++, user.getUsername());
            preparedStatement.setString(counter++, user.getPassword());

            return preparedStatement;
        }, keyHolder);

        return keyHolder.getKeyAs(UUID.class);
    }

    @Override
    public boolean checkUserExistsByLogin(@NonNull String login) {
        Long count = postgresJdbcTemplate.query((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("select count(1) from users where login = ?");
            preparedStatement.setString(1, login.toLowerCase(Locale.ROOT));

            return preparedStatement;
        }, (resultSet) -> {
            if (!resultSet.next()) {
                return 0L;
            }

            return resultSet.getLong(1);
        });

        return count > 0;
    }

    @Override
    public User getByLogin(@NonNull String login) {
        return postgresJdbcTemplate.query((connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    """
                        select u.id, u.username, u.email, u.login, u.password from users u
                        where u.login = ?
                    """
            );
            preparedStatement.setString(1, login);

            return preparedStatement;
        }, (resultSet) -> {
            resultSet.next();
            return User.builder()
                    .id(UUID.fromString(resultSet.getString(1)))
                    .email(resultSet.getString(3))
                    .login(resultSet.getString(4))
                    .username(resultSet.getString(2))
                    .password(resultSet.getString(5))
                    .build();
        });
    }
}
