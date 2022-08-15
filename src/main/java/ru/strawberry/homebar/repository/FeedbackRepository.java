package ru.strawberry.homebar.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.strawberry.homebar.entity.Feedback;

/**
 * Repository for interaction with Feedback db.
 *
 * @author RBeschastnykh
 */
@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
  Optional<Feedback> findByCocktailId(Long cocktailId);
}
