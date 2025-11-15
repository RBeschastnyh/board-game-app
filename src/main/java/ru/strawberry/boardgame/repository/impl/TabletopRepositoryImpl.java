package ru.strawberry.boardgame.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.QueryHints;
import ru.strawberry.boardgame.exceptions.DatabaseException;
import ru.strawberry.boardgame.exceptions.TableSearchException;
import ru.strawberry.boardgame.repository.dto.Party;
import ru.strawberry.boardgame.repository.dto.Tabletop;
import ru.strawberry.boardgame.repository.dto.User;
import ru.strawberry.boardgame.repository.TabletopRepository;
import ru.strawberry.boardgame.sesion.SessionContextImpl;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Slf4j
public class TabletopRepositoryImpl implements TabletopRepository {

    @Override
    public Optional<Tabletop> checkIfTableExistsByCode(String code) {
        Transaction transaction = null;
        try (Session session = SessionContextImpl.getInstance().getSession()) {
            log.trace("Begin transaction");
            transaction = session.beginTransaction();

            Tabletop tabletop = session.createQuery("select t from Tabletop t where t.code = :code", Tabletop.class)
                    .setHint(QueryHints.READ_ONLY, true)
                    .setParameter("code", code)
                    .getSingleResult();
            transaction.commit();
            log.trace("End transaction");

            return Optional.of(tabletop);
        } catch (NoResultException noResultException) {
            log.info("No table found for code {}", code);
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error while checking table existence", e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new TableSearchException("Ошибка при создании стола. Попробуйте позже");
        }
    }

    @Override
    public void addTable(Long tgId, String code) {
        Transaction transaction = null;
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Europe/Moscow"));

        Tabletop tabletop = new Tabletop();
        tabletop.setCode(code);
        tabletop.setCreatedAt(dateTime);
        tabletop.setExpiresAt(dateTime.plusHours(12));
        tabletop.setIsShut(Boolean.FALSE);

        try (Session session = SessionContextImpl.getInstance().getSession()) {
            log.trace("Begin transaction");
            transaction = session.beginTransaction();
            User user = session.createQuery("select u from User u where u.tgId = :id", User.class)
                    .setHint(QueryHints.READ_ONLY, true)
                    .setParameter("id", tgId)
                    .getSingleResult();
            tabletop.setHostId(user.getId());
            session.persist(tabletop);

            Party party = new Party();
            party.setTableId(tabletop.getId());
            party.setUserId(user.getId());
            party.setState(Boolean.TRUE);

            session.persist(party);

            session.flush();

            transaction.commit();
            log.trace("End transaction");
        } catch (NoResultException noResultException) {
            log.error("No user found by id {}", tgId);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Возникла ошибка, попробуйте позже");
        } catch (Exception e) {
            log.error("Error while checking table existence", e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new TableSearchException("Ошибка при создании стола. Попробуйте позже");
        }
    }

    @Override
    public Optional<Tabletop> checkIfUserHasTable(Long tgId) {
        Transaction transaction = null;
        try (Session session = SessionContextImpl.getInstance().getSession()) {
            log.trace("Begin transaction");
            transaction = session.beginTransaction();

            List<Tabletop> tabletops = session.createQuery("select t from Tabletop t inner " +
                            "join Party p on p.tableId = t.id " +
                            "join User u on p.userId = u.id where u.tgId = :id", Tabletop.class)
                    .setHint(QueryHints.READ_ONLY, true)
                    .setParameter("id", tgId)
                    .getResultList();
            transaction.commit();
            log.trace("End transaction");

            if (tabletops.size() > 1) {
                throw new TableSearchException("Возникла ошибка получения информации о Ваших активных столах!");
            }

            return tabletops.isEmpty() ? Optional.empty() : Optional.of(tabletops.get(0));
        } catch (Exception e) {
            log.error("Error while checking table existence", e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new TableSearchException("Ошибка при создании стола. Попробуйте позже");
        }
    }

    @Override
    public void deleteTable(Tabletop tabletop) {
        log.info("Deleting table with code {}", tabletop.getCode());
        Transaction transaction = null;
        try (Session session = SessionContextImpl.getInstance().getSession()) {
            log.trace("Begin transaction");
            transaction = session.beginTransaction();

            List<Party> parties = session.createQuery("select p from Party p where p.tableId = :id", Party.class)
                    .setParameter("id", tabletop.getId())
                    .setHint(QueryHints.READ_ONLY, true)
                    .getResultList();

            parties.forEach(session::delete);
            session.delete(tabletop);

            session.flush();
            transaction.commit();
            log.trace("End transaction");
        } catch (NoResultException nex) {
            log.error("No party for a table..", nex);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new TableSearchException("Ошибка при удалении стола. Попробуйте позже");
        } catch (Exception e) {
            log.error("Error while deleting table");
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public Optional<Tabletop> checkIfUserIsHost(Long tgId) {
        Transaction transaction = null;
        try (Session session = SessionContextImpl.getInstance().getSession()) {
            log.trace("Begin transaction");
            transaction = session.beginTransaction();
            Tabletop tabletop = session.createQuery("select t from Tabletop t join User u on u.id = t.hostId where u.tgId = :id", Tabletop.class)
                    .setHint(QueryHints.READ_ONLY, true)
                    .setParameter("id", tgId)
                    .getSingleResult();
            transaction.commit();
            log.trace("End transaction");
            return Optional.of(tabletop);
        } catch (NoResultException nex) {
            log.info("No table found for user {}", tgId);
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error while checking table existence", e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new TableSearchException("Ошибка при создании стола. Попробуйте позже");
        }
    }
}
