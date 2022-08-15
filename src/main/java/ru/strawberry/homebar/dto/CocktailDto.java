package ru.strawberry.homebar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

/**
 * Response entity for cocktail api.
 *
 * @author RBeschastnykh
 */
@Data
public class CocktailDto {

  @Schema(title = "Unique identifier")
  private Long id;

  @Schema(title = "The name of the cocktail", required = true, example = "Tuxedo")
  private String cocktailName;

  @Schema(title = "Cocktail description", required = true, example = "Освежает и вставляет")
  private String description;

  @Schema(title = "Allergens in cocktail", required = true)
  private List<String> allergens;

  @Schema(title = "List of ingredients", required = true)
  private List<String> ingredients;

  @Schema(title = "List of feedbacks")
  private List<FeedbackDto> feedbacks;

  @Schema(title = "Tags to find cocktail in a list", required = true, example = "Горький")
  private List<String> tags;

  @Schema(title = "Cocktail average rating from 1 to 10", example = "8.3")
  private Double rating;
}
