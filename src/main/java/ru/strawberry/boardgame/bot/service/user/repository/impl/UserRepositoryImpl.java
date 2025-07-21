package ru.strawberry.boardgame.bot.service.user.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.annotations.QueryHints;
import ru.strawberry.boardgame.bot.service.user.repository.UserRepository;
import ru.strawberry.boardgame.dto.User;
import ru.strawberry.boardgame.sesion.SessionContextImpl;

import javax.persistence.NoResultException;

@Slf4j
public class UserRepositoryImpl implements UserRepository {
    @Override
    public boolean checkIfUserExists(Long tgId) {
        try (Session session = SessionContextImpl.getInstance().getSession()) {
            session.beginTransaction();
            session.createQuery("SELECT u FROM User u WHERE u.tgId = :tgId", User.class)
                    .setHint(QueryHints.READ_ONLY, true)
                    .setParameter("tgId", tgId)
                    .getSingleResult();
        } catch (NoResultException nrex) {
            return false;
        } catch (Exception ex) {
            log.error("Error while getting user from DB: {}", ex.getMessage());
            throw new RuntimeException("Возникла ошибка, попробуйте позже");
        }

        return true;
    }

    @Override
    public void createUser(Long tgId) {
        Session session = SessionContextImpl.getInstance().getSession();

        session.beginTransaction();

        User user = new User();
        user.setTgId(tgId);

        session.persist(user);
        session.flush();
        session.close();
    }

    @Override
    public boolean checkIfTeseraUserIsRegistered(Long tgId) {
        try (Session session = SessionContextImpl.getInstance().getSession()) {
            session.beginTransaction();
            session.createQuery("SELECT u FROM User u WHERE u.tgId = :tgId and u.teseraName is not null", User.class)
                    .setHint(QueryHints.READ_ONLY, true)
                    .setParameter("tgId", tgId)
                    .getSingleResult();
        } catch (NoResultException nrex) {
            return false;
        } catch (Exception ex) {
            log.error("Error while getting user from DB: {}", ex.getMessage());
            throw new RuntimeException("Возникла ошибка, попробуйте позже");
        }

        return true;
    }
}
