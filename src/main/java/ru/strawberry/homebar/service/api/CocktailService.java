package ru.strawberry.homebar.service.api;

import java.util.List;
import ru.strawberry.homebar.dto.CocktailDto;

/**
 * Service for operating cocktail requests.
 *
 * @author RBeschastnykh
 */
public interface CocktailService {

  /**
   * Gets all cocktails from db.
   *
   * @return list of all available cocktails
   * @see CocktailDto
   * @since 1.0
   */
  List<CocktailDto> getAllCocktails();

  /**
   * Gets all cocktails suits filter.
   *
   * @return list of cocktails with applied filter
   * @see CocktailDto
   * @since 1.0
   */
  List<CocktailDto> getAllCocktailsApplyFilter();

}
