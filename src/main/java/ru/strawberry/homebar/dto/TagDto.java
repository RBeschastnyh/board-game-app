package ru.strawberry.homebar.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagDto {
  private Long id;
  private String tagName;
  private String tagDescription;
}
