package ru.strawberry.homebar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.strawberry.homebar.entity.Cocktail;

/**
 * Repository to work with cocktail entities.
 *
 * @author RBeschastnykh
 */
@Repository
public interface CocktailRepository extends JpaRepository<Cocktail, Long> {}
