package ru.strawberry.homebar.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Response entity for cocktail api.
 *
 * @author RBeschastnykh
 */
@Getter
@Setter
public class CocktailDto {

  @Schema(title = "Unique identifier", example = "1")
  private Long id;

  @Schema(title = "The name of the cocktail", example = "Tuxedo")
  private String name;

  @Schema(title = "List of ingredients")
  private String ingredients;

  @Schema(title = "Cocktail description")
  private String description;

  @Schema(title = "Cocktail supply")
  private String supply;

  @Schema(title = "Cocktail group id")
  private Long groupId;

  @Schema(title = "Tags to find cocktail in a list", example = "Горький")
  private List<TagDto> tags;
}
