package ru.strawberry.homebar.mapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.mapstruct.Mapper;
import ru.strawberry.homebar.dto.CocktailDto;
import ru.strawberry.homebar.entity.Cocktail;

/**
 * Mapper for cocktail entities.
 *
 * @author RBeschastnykh
 */
@Mapper(
    componentModel = "spring",
    uses = {FeedbackMapper.class})
public interface CocktailMapper {

  List<CocktailDto> toListDto(List<Cocktail> source);

  CocktailDto toDto(Cocktail source);

  default List<String> stringToList(String source) {
    return Optional.ofNullable(source)
        .map(s -> source.split(", "))
        .map(Arrays::asList)
        .orElse(null);
  }
}
