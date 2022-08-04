package ru.strawberry.homebar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.strawberry.homebar.entity.Cocktail;

/**
 * Repository to work with cocktail entities.
 *
 * @author RBeschastnykh
 */
public interface CocktailRepository extends JpaRepository<Cocktail, Long> {

}
