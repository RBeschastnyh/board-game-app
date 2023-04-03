package ru.strawberry.homebar.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import ru.strawberry.homebar.dto.CocktailDto;
import ru.strawberry.homebar.domain.entity.Cocktail;

/**
 * Mapper for cocktail entities.
 *
 * @author RBeschastnykh
 */
@Mapper(componentModel = "spring")
public interface CocktailMapper {

  List<CocktailDto> toListDto(List<Cocktail> source);

  CocktailDto toDto(Cocktail source);
}
