package ru.strawberry.homebar.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CocktailFilterDto {
  private Long groupId;
  private List<String> tags;
}
