package ru.strawberry.homebar.domain.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.strawberry.homebar.domain.entity.Guest;

/**
 * Repository for interaction with Guest db.
 *
 * @author RBeschastnykh
 */
@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

  Optional<Guest> findOneByLogin(String login);
}
