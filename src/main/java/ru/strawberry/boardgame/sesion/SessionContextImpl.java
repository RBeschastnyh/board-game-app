package ru.strawberry.boardgame.sesion;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.postgresql.Driver;
import ru.strawberry.boardgame.dto.User;

import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionContextImpl implements SessionContext {

    private static SessionContextImpl instance;

    private SessionFactory sessionFactory;

    public static synchronized SessionContextImpl getInstance() {
        if (instance == null) {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(User.class);

            Properties properties = new Properties();
            properties.setProperty("hibernate.connection.url", System.getenv("FLYWAY_URL"));
            properties.setProperty("hibernate.connection.username", System.getenv("FLYWAY_USER"));
            properties.setProperty("hibernate.connection.password", System.getenv("FLYWAY_PASSWORD"));

            properties.setProperty("hibernate.connection.driver_class", Driver.class.getCanonicalName());
            properties.setProperty("hibernate.default_schema", System.getenv("FLYWAY_SCHEMA"));

            instance = new SessionContextImpl(configuration.addProperties(properties).buildSessionFactory());
        }

        return instance;
    }

    @Override
    public Session getSession() {
        return sessionFactory.openSession();
    }
}
