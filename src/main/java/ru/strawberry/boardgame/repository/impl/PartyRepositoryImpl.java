package ru.strawberry.boardgame.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.strawberry.boardgame.exceptions.DatabaseException;
import ru.strawberry.boardgame.exceptions.SadException;
import ru.strawberry.boardgame.repository.dto.Party;
import ru.strawberry.boardgame.repository.PartyRepository;
import ru.strawberry.boardgame.repository.UserRepository;
import ru.strawberry.boardgame.repository.dto.User;
import ru.strawberry.boardgame.sesion.SessionContextImpl;

import java.util.Optional;

@Slf4j
public class PartyRepositoryImpl implements PartyRepository {

    private final UserRepository userRepository = new UserRepositoryImpl();

    @Override
    public void addUserToTable(Long tgIg, Long tableId) {
        log.info("Adding user with telegram id {} to table {}", tgIg, tableId);
        Transaction transaction = null;
        try (Session session = SessionContextImpl.getInstance().getSession()) {
            log.trace("Begin transaction");
            transaction = session.beginTransaction();

            userRepository.getByTgId(tgIg)
                    .ifPresent(user -> {
                        Party party = new Party();
                        party.setTableId(tableId);
                        party.setUserId(user.getId());

                        session.persist(party);
                        session.flush();
                    });

            transaction.commit();
            log.trace("End transaction");
        } catch (Exception e) {
            log.error("Error while adding user {} to pArTy table {}", tgIg, tableId, e);
            if (transaction != null) {
                transaction.rollback();
            }
            throw new SadException("Не получилось присоединить Вас к столу..");
        }
    }

    @Override
    public void removeUserFromTable(Long tgId) {
        Transaction transaction = null;
        try (Session session = SessionContextImpl.getInstance().getSession()) {
            transaction = session.beginTransaction();

            Optional<User> user = userRepository.getByTgId(tgId);
            if (user.isPresent()) {
                session.createQuery("delete from Party p where p.userId = :id")
                        .setParameter("id", user.get().getId())
                        .executeUpdate();
            } else {
                throw new DatabaseException();
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DatabaseException("Ошибка при отсоединении от стола");
        }
    }
}
