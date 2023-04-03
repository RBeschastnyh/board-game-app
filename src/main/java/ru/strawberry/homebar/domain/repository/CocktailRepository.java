package ru.strawberry.homebar.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.strawberry.homebar.domain.entity.Cocktail;

/**
 * Repository to work with cocktail entities.
 *
 * @author RBeschastnykh
 */
@Repository
public interface CocktailRepository extends PagingAndSortingRepository<Cocktail, Long>, JpaSpecificationExecutor<Cocktail> {}
