package ru.strawberry.homebar.dto;

import lombok.Data;

/**
 * An object for feedback and cocktail api.
 *
 * @author RBeschastnykh
 */
@Data
public class FeedbackDto {

  private Long id;
  private String author;
  private Integer rating;
  private String text;

}
