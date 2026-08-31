package ru.strawberry.boardgame.sesion;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.postgresql.Driver;
import ru.strawberry.boardgame.bot.util.EnvVars;
import ru.strawberry.boardgame.repository.dto.Games;
import ru.strawberry.boardgame.repository.dto.Party;
import ru.strawberry.boardgame.repository.dto.Tabletop;
import ru.strawberry.boardgame.repository.dto.User;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionContextImpl implements SessionContext {

    private static SessionContextImpl instance;

    private SessionFactory sessionFactory;

    public static synchronized SessionContextImpl getInstance() {
        if (instance == null) {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Games.class);
            configuration.addAnnotatedClass(Tabletop.class);
            configuration.addAnnotatedClass(Party.class);

            Properties properties = new Properties();
            properties.setProperty("hibernate.connection.url", System.getenv(EnvVars.FLYWAY_URL.name()));
            properties.setProperty("hibernate.connection.username", System.getenv(EnvVars.FLYWAY_USER.name()));
            properties.setProperty("hibernate.connection.password", System.getenv(EnvVars.FLYWAY_PASSWORD.name()));

            properties.setProperty("hibernate.connection.driver_class", Driver.class.getCanonicalName());
            properties.setProperty("hibernate.default_schema", System.getenv(EnvVars.FLYWAY_SCHEMA.name()));

            instance = new SessionContextImpl(configuration.addProperties(properties).buildSessionFactory());
        }

        return instance;
    }

    @Override
    public Session getSession() {
        return sessionFactory.openSession();
    }
}
