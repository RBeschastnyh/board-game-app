package ru.strawberry.homebar.service.api;

import java.util.List;
import ru.strawberry.homebar.dto.CocktailDto;
import ru.strawberry.homebar.dto.FeedbackDto;
import ru.strawberry.homebar.exception.NotFoundException;

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
   * Creates or updates users' cocktail rate.
   *
   * @param cocktailId cocktail unique identifier
   * @param rateDto rate info
   * @see FeedbackDto
   * @since 1.0
   */
  void createFeedback(Long cocktailId, FeedbackDto rateDto) throws NotFoundException;
}
