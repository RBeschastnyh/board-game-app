package ru.strawberry.boardgame.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.QueryHints;
import ru.strawberry.boardgame.exceptions.DatabaseException;
import ru.strawberry.boardgame.repository.UserRepository;
import ru.strawberry.boardgame.repository.dto.User;
import ru.strawberry.boardgame.sesion.SessionContextImpl;

import javax.persistence.NoResultException;
import java.util.Optional;

@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @Override
    public void createUser(Long tgId) {
        log.info("Creating user with tgId {}", tgId);

        Transaction transaction = null;
        try (Session session = SessionContextImpl.getInstance().getSession()) {
            log.trace("Begin transaction");
            transaction = session.beginTransaction();

            User user = new User();
            user.setTgId(tgId);

            session.persist(user);
            session.flush();

            transaction.commit();
            log.trace("End transaction");
        } catch (Exception e) {
            log.error("Error while creating user", e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public boolean checkIfUserExists(Long tgId) {
        log.info("Searching for tg user {} in db", tgId);
        Transaction transaction = null;
        try (Session session = SessionContextImpl.getInstance().getSession()) {
            log.trace("Begin transaction");
            transaction = session.beginTransaction();
            session.createQuery("SELECT u FROM User u WHERE u.tgId = :tgId", User.class)
                    .setHint(QueryHints.READ_ONLY, true)
                    .setParameter("tgId", tgId)
                    .getSingleResult();

            transaction.commit();
            log.trace("End transaction");
        } catch (NoResultException nrex) {
            log.info("Not found user with tg id {}", tgId);
            return false;
        } catch (Exception ex) {
            log.error("Error while getting user from DB: {}", ex.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Возникла ошибка, попробуйте позже");
        }

        return true;
    }

    @Override
    public boolean checkIfTeseraUserIsRegistered(Long tgId) {
        log.info("Checking if tg user {} has registered tesera account", tgId);
        Transaction transaction = null;
        try (Session session = SessionContextImpl.getInstance().getSession()) {
            log.trace("Begin transaction");
            transaction = session.beginTransaction();
            session.createQuery("SELECT u FROM User u WHERE u.tgId = :tgId and u.teseraName is not null", User.class)
                    .setHint(QueryHints.READ_ONLY, true)
                    .setParameter("tgId", tgId)
                    .getSingleResult();
            transaction.commit();
            log.trace("End transaction");
        } catch (NoResultException nrex) {
            return false;
        } catch (Exception ex) {
            log.error("Error while getting user from DB: {}", ex.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Возникла ошибка, попробуйте позже");
        }

        return true;
    }

    @Override
    public User setTeseraAccountName(Long tgId, String login, Long teseraId) {
        log.info("Setting tesera account name: {} for telegram user {}", login, tgId);
        Transaction transaction = null;
        try (Session session = SessionContextImpl.getInstance().getSession()) {
            log.trace("Begin transaction");
            transaction = session.beginTransaction();
            User user = session.createQuery("SELECT u FROM User u WHERE u.tgId = :tgId", User.class)
                    .setHint(QueryHints.READ_ONLY, false)
                    .setParameter("tgId", tgId)
                    .getSingleResult();
            user.setTeseraName(login);

            session.persist(user);
            session.flush();
            transaction.commit();
            log.trace("End transaction");

            return user;
        } catch (Exception ex) {
            log.error("Error while getting user from DB: {}", ex.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }

            throw new DatabaseException("Возникла ошибка, попробуйте позже");
        }
    }

    @Override
    public Optional<User> getByTgId(Long tgId) {
        log.info("Getting user by tg id: {}", tgId);
        Transaction transaction = null;
        try (Session session = SessionContextImpl.getInstance().getSession()) {
            log.trace("Begin transaction");
            transaction = session.beginTransaction();
            User user = session.createQuery("select u from User u where u.tgId = :id", User.class)
                    .setHint(QueryHints.READ_ONLY, true)
                    .setParameter("id", tgId)
                    .getSingleResult();

            transaction.commit();
            log.trace("End transaction");
            return Optional.of(user);
        } catch (NoResultException nex) {
            log.info("User with tgId = {} not found!", tgId);
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error while getting user by tgid = {}", tgId);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Ваш пользователь не найден!");
        }
    }
}
