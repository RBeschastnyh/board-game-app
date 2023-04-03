package ru.strawberry.homebar.domain.spec;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import ru.strawberry.homebar.domain.entity.Cocktail;
import ru.strawberry.homebar.domain.entity.Cocktail_;
import ru.strawberry.homebar.domain.entity.Tags_;
import ru.strawberry.homebar.dto.CocktailFilterDto;

@Component
public class CocktailSpecificationService {

  public Specification<Cocktail> getTrueSpec() {
    return (r, q, cb) -> cb.conjunction();
  }

  public Specification<Cocktail> getCocktailFilterSpec(CocktailFilterDto filterDto) {
    return getCocktailGroupSpec(filterDto.getGroupId())
        .and(getCocktailTagSpec(filterDto.getTags()));
  }

  private Specification<Cocktail> getCocktailTagSpec(List<String> tags) {
    if (tags != null) {
      return (r, q, cb) -> r.join(Cocktail_.TAGS).get(Tags_.TAG_NAME).in(tags);
    }
    return (r, q, cb) -> cb.conjunction();
  }

  private Specification<Cocktail> getCocktailGroupSpec(Long id) {
    if (id != null) {
      return (r, q, cb) -> cb.equal(r.get(Cocktail_.GROUP_ID), id);
    }
    return (r, q, cb) -> cb.conjunction();
  }

}
