package ru.strawberry.homebar.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * An object for feedback and cocktail api.
 *
 * @author RBeschastnykh
 */
@Data
public class FeedbackDto {

  @Schema(title = "Author identifier", required = true)
  private Long authorId;

  @Schema(title = "Author identifier", required = true)
  private Integer rate;

  @Schema(title = "Feedback text")
  private String text;
}
