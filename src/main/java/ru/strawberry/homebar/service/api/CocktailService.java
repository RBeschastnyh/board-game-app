package ru.strawberry.homebar.service.api;

import java.util.List;
import ru.strawberry.homebar.dto.CocktailDto;
import ru.strawberry.homebar.dto.CocktailFilterDto;

/**
 * Service for operating cocktail requests.
 *
 * @author RBeschastnykh
 */
public interface CocktailService {

  /**
   * Gets all cocktails from db.
   *
   * @param filterDto cockatail request filter
   *
   * @return list of available cocktails matching filter
   * @see CocktailDto
   * @since 1.0
   */
  List<CocktailDto> getAllCocktails(CocktailFilterDto filterDto);

  /**
   * Get cocktail specified by unique id.
   *
   * @param id unique identifier
   * @return cocktail, if exists
   * @since 1.0
   */
  CocktailDto getById(Long id);
}
